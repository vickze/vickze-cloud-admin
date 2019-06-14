package io.vickze.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.vickze.auth.domain.DO.*;
import io.vickze.auth.domain.DTO.AssignMenuResourceDTO;
import io.vickze.auth.mapper.*;
import io.vickze.auth.service.MenuResourceService;
import io.vickze.auth.service.SystemService;
import io.vickze.common.domain.RPage;
import io.vickze.auth.domain.DTO.RoleQueryDTO;
import io.vickze.common.enums.DBOrder;
import io.vickze.auth.service.RoleService;
import io.vickze.auth.exception.ForbiddenException;
import javafx.util.Pair;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-16 11:16:31
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMenuResourceMapper roleMenuResourceMapper;
    @Autowired
    private MenuResourceService menuResourceMapper;

    @Autowired
    private SystemService systemService;

    @Override
    public Pair<List<RoleDO>, Long> list(RoleQueryDTO queryDTO) {
        IPage<RoleDO> page = new RPage<>(queryDTO.getOffset(), queryDTO.getLimit());
        QueryWrapper<RoleDO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(queryDTO.getName())) {
            wrapper = wrapper.eq("name", queryDTO.getName());
        }
        if (StringUtils.isNotBlank(queryDTO.getRemark())) {
            wrapper = wrapper.eq("remark", queryDTO.getRemark());
        }
        if (queryDTO.getField() != null) {
            wrapper = wrapper.orderBy(true, DBOrder.ASC.name().equals(queryDTO.getOrder()),
                    com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(queryDTO.getField()));
        } else {
            wrapper = wrapper.orderByDesc("id");
        }
        roleMapper.selectPage(page, wrapper);
        return new Pair<>(page.getRecords(), page.getTotal());
    }

    @Override
    public RoleDO get(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public void insert(RoleDO roleDO) {
        roleMapper.insert(roleDO);
    }

    @Override
    public void update(RoleDO roleDO) {
        roleMapper.updateById(roleDO);
    }

    @Transactional
    @Override
    public void delete(Long... ids) {
        QueryWrapper<RoleMenuResourceDO> wrapper = new QueryWrapper<>();
        wrapper.in("role_id", ids);
        roleMenuResourceMapper.delete(wrapper);
        roleMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Transactional
    @Override
    public void assignMenuResource(AssignMenuResourceDTO assignMenuResourceDTO) {
        QueryWrapper<RoleMenuResourceDO> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", assignMenuResourceDTO.getRoleId());
        wrapper.eq("system_id", assignMenuResourceDTO.getSystemId());
        roleMenuResourceMapper.delete(wrapper);
        for (Long menuResourceId : assignMenuResourceDTO.getMenuResourceIds()) {
            RoleMenuResourceDO roleMenuResourceDO = new RoleMenuResourceDO();
            roleMenuResourceDO.setRoleId(assignMenuResourceDTO.getRoleId());
            roleMenuResourceDO.setSystemId(assignMenuResourceDTO.getSystemId());
            roleMenuResourceDO.setMenuResourceId(menuResourceId);
            roleMenuResourceMapper.insert(roleMenuResourceDO);
        }
    }

    @Override
    public List<MenuResourceDO> getMenuResources(String systemKey, Long... roleIds) {
        SystemDO systemDO = systemService.selectByKey(systemKey);
        if (systemDO == null) {
            throw new ForbiddenException();
        }

        return getMenuResources(systemDO.getId(), roleIds);
    }

    @Override
    public List<RoleDO> selectByUserId(Long userId) {
        QueryWrapper<UserRoleDO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<UserRoleDO> userRoleDOList = userRoleMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(userRoleDOList)) {
            return new ArrayList<>();
        }
        return roleMapper.selectBatchIds(userRoleDOList.stream().map(UserRoleDO::getRoleId)
                .collect(Collectors.toList()));
    }

    @Override
    public List<MenuResourceDO> getMenuResources(Long systemId, Long... roleIds) {
        if (roleIds.length == 0) {
            return new ArrayList<>();
        }
        QueryWrapper<RoleMenuResourceDO> wrapper = new QueryWrapper<>();

        wrapper.in("role_id", roleIds);
        wrapper.eq("system_id", systemId);
        List<RoleMenuResourceDO> list = roleMenuResourceMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }

        QueryWrapper<MenuResourceDO> menuResourceDOQueryWrapper = new QueryWrapper<>();
        menuResourceDOQueryWrapper.in("id", list.stream().map(RoleMenuResourceDO::getMenuResourceId)
                .collect(Collectors.toList()));
        menuResourceDOQueryWrapper.eq("system_id", systemId);
        return menuResourceMapper.selectList(menuResourceDOQueryWrapper);
    }


}
