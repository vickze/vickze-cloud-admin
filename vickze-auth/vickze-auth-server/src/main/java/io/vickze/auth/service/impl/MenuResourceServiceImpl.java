package io.vickze.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.vickze.auth.domain.DO.MenuResourceInterfaceDO;
import io.vickze.auth.domain.DO.SystemDO;
import io.vickze.auth.domain.DTO.MenuResourceDTO;
import io.vickze.auth.mapper.MenuResourceInterfaceMapper;
import io.vickze.auth.mapper.MenuResourceMapper;
import io.vickze.auth.service.SystemService;
import io.vickze.common.domain.RPage;
import io.vickze.auth.domain.DO.MenuResourceDO;
import io.vickze.auth.domain.DTO.MenuResourceQueryDTO;
import io.vickze.common.enums.DBOrder;
import io.vickze.auth.service.MenuResourceService;
import io.vickze.auth.exception.ForbiddenException;
import io.vickze.common.util.InterfaceUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单资源
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-29 15:01:06
 */
@Service
public class MenuResourceServiceImpl implements MenuResourceService {
    @Autowired
    private SystemService systemService;
    @Autowired
    private MenuResourceInterfaceMapper menuResourceInterfaceMapper;
    @Autowired
    private MenuResourceMapper menuResourceMapper;


    @Override
    public Pair<List<MenuResourceDTO>, Long> list(MenuResourceQueryDTO queryDTO) {
        IPage<MenuResourceDO> page = new RPage<>(queryDTO.getOffset(), queryDTO.getLimit());
        QueryWrapper<MenuResourceDO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(queryDTO.getMenuId())) {
            wrapper = wrapper.eq("menu_id", queryDTO.getMenuId());
        }
        if (queryDTO.getField() != null) {
            wrapper = wrapper.orderBy(true, DBOrder.ASC.name().equals(queryDTO.getOrder()),
                    com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(queryDTO.getField()));
        } else {
            wrapper = wrapper.orderByDesc("id");
        }
        menuResourceMapper.selectPage(page, wrapper);

        List<MenuResourceDTO> result = page.getRecords().stream().map(menuResourceDO -> {
            MenuResourceDTO menuResourceDTO = new MenuResourceDTO();
            BeanUtils.copyProperties(menuResourceDO, menuResourceDTO);

            QueryWrapper<MenuResourceInterfaceDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("menu_resource_id", menuResourceDTO.getId());
            menuResourceDTO.setInterfaces(menuResourceInterfaceMapper.selectList(queryWrapper));
            return menuResourceDTO;
        }).collect(Collectors.toList());
        return Pair.of(result, page.getTotal());
    }

    @Override
    public MenuResourceDTO get(Long id) {
        MenuResourceDO menuResourceDO = menuResourceMapper.selectById(id);
        if (menuResourceDO == null) {
            return null;
        }
        MenuResourceDTO menuResourceDTO = new MenuResourceDTO();
        BeanUtils.copyProperties(menuResourceDO, menuResourceDTO);

        QueryWrapper<MenuResourceInterfaceDO> wrapper = new QueryWrapper<>();
        wrapper.eq("menu_resource_id", menuResourceDTO.getId());
        menuResourceDTO.setInterfaces(menuResourceInterfaceMapper.selectList(wrapper));

        return menuResourceDTO;
    }

    @Override
    @Transactional
    public void insert(MenuResourceDTO menuResourceDTO) {
        MenuResourceDO menuResourceDO = new MenuResourceDO();
        BeanUtils.copyProperties(menuResourceDTO, menuResourceDO);
        menuResourceMapper.insert(menuResourceDO);
        menuResourceDTO.getInterfaces().forEach(menuResourceInterfaceDO -> {
            menuResourceInterfaceDO.setSystemId(menuResourceDO.getSystemId());
            menuResourceInterfaceDO.setMenuId(menuResourceDO.getMenuId());
            menuResourceInterfaceDO.setMenuResourceId(menuResourceDO.getId());
            menuResourceInterfaceMapper.insert(menuResourceInterfaceDO);
        });
    }

    @Override
    public void update(MenuResourceDTO menuResourceDTO) {
        MenuResourceDO menuResourceDO = new MenuResourceDO();
        BeanUtils.copyProperties(menuResourceDTO, menuResourceDO);
        menuResourceMapper.updateById(menuResourceDO);

        QueryWrapper<MenuResourceInterfaceDO> wrapper = new QueryWrapper<>();
        wrapper.eq("menu_resource_id", menuResourceDTO.getId());
        menuResourceInterfaceMapper.delete(wrapper);
        menuResourceDTO.getInterfaces().forEach(menuResourceInterfaceDO -> {
            menuResourceInterfaceDO.setSystemId(menuResourceDO.getSystemId());
            menuResourceInterfaceDO.setMenuId(menuResourceDO.getMenuId());
            menuResourceInterfaceDO.setMenuResourceId(menuResourceDO.getId());
            menuResourceInterfaceMapper.insert(menuResourceInterfaceDO);
        });
    }

    @Transactional
    @Override
    public void delete(Long... ids) {
        QueryWrapper<MenuResourceInterfaceDO> wrapper = new QueryWrapper<>();
        wrapper.in("menu_resource_id", ids);
        menuResourceInterfaceMapper.delete(wrapper);
        menuResourceMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public List<String> getPermissions(String systemKey, String uri, String method) {
        if (StringUtils.isBlank(systemKey)) {
            throw new ForbiddenException(false);
        }
        SystemDO systemDO = systemService.selectByKey(systemKey);
        if (systemDO == null) {
            throw new ForbiddenException(false);
        }

        return getPermissions(systemDO.getId(), uri, method);
    }

    @Override
    public List<String> getPermissions(Long systemId, String uri, String method) {
        List<String> list = new ArrayList<>();
        QueryWrapper<MenuResourceInterfaceDO> wrapper = new QueryWrapper<>();
        wrapper.eq("system_id", systemId);
        wrapper.eq("interface_method", method);

        List<MenuResourceInterfaceDO> menuResourceInterfaceDOS = menuResourceInterfaceMapper.selectList(wrapper);

        for (MenuResourceInterfaceDO menuResourceInterfaceDO : menuResourceInterfaceDOS) {
            String interfaceUri = menuResourceInterfaceDO.getInterfaceUri();

            if (InterfaceUtil.uriMatch(uri, interfaceUri)) {
                MenuResourceDO menuResourceDO = menuResourceMapper.selectById(menuResourceInterfaceDO.getMenuResourceId());
                if (menuResourceDO != null) {
                    list.add(menuResourceDO.getPermission());
                }
            }
        }
        return list;
    }

    @Override
    public List<MenuResourceDO> selectList(QueryWrapper<MenuResourceDO> wrapper) {
        return menuResourceMapper.selectList(wrapper);
    }

}
