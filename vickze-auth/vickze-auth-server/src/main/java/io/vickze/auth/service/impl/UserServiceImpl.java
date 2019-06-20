package io.vickze.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.vickze.auth.constant.GlobalConstant;
import io.vickze.auth.domain.DO.MenuResourceDO;
import io.vickze.auth.domain.DO.UserRoleDO;
import io.vickze.auth.domain.DTO.UserDTO;
import io.vickze.auth.enums.UserStatus;
import io.vickze.auth.mapper.UserMapper;
import io.vickze.auth.mapper.UserRoleMapper;
import io.vickze.auth.service.RoleService;
import io.vickze.common.domain.RPage;
import io.vickze.auth.domain.DO.UserDO;
import io.vickze.auth.domain.DTO.UserQueryDTO;
import io.vickze.common.enums.DBOrder;
import io.vickze.auth.service.UserService;

import java.util.*;

import io.vickze.common.exception.MessageException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-16 11:16:31
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public Pair<List<UserDTO>, Long> list(UserQueryDTO queryDTO) {
        IPage<UserDO> page = new RPage<>(queryDTO.getOffset(), queryDTO.getLimit());
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(queryDTO.getUsername())) {
            wrapper = wrapper.eq("username", queryDTO.getUsername());
        }
        if (StringUtils.isNotBlank(queryDTO.getEmail())) {
            wrapper = wrapper.eq("email", queryDTO.getEmail());
        }
        if (StringUtils.isNotBlank(queryDTO.getMobile())) {
            wrapper = wrapper.eq("mobile", queryDTO.getMobile());
        }
        if (!CollectionUtils.isEmpty(queryDTO.getStatus())) {
            wrapper = wrapper.in("status", queryDTO.getStatus());
        }
        if (queryDTO.getField() != null) {
            wrapper = wrapper.orderBy(true, DBOrder.ASC.name().equals(queryDTO.getOrder()),
                    com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(queryDTO.getField()));
        } else {
            wrapper = wrapper.orderByDesc("id");
        }
        userMapper.selectPage(page, wrapper);

        List<UserDTO> result = page.getRecords().stream().map(userDO -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userDO, userDTO);
            userDTO.setRoles(roleService.selectByUserId(userDTO.getId()));
            return userDTO;
        }).collect(Collectors.toList());
        return Pair.of(result, page.getTotal());
    }

    @Override
    public UserDTO get(Long id) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userMapper.selectById(id), userDTO);
        userDTO.setRoles(roleService.selectByUserId(userDTO.getId()));
        return userDTO;
    }

    @Transactional
    @Override
    public void insert(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        userDO.setPassword(BCrypt.hashpw(userDO.getPassword(), BCrypt.gensalt()));
        userMapper.insert(userDO);

        userDTO.getRoles().forEach(roleDO -> {
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setUserId(userDO.getId());
            userRoleDO.setRoleId(roleDO.getId());
            userRoleMapper.insert(userRoleDO);
        });
    }

    @Transactional
    @Override
    public void update(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        userDO.setPassword(BCrypt.hashpw(userDO.getPassword(), BCrypt.gensalt()));
        userMapper.updateById(userDO);

        QueryWrapper<UserRoleDO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userDTO.getId());
        userRoleMapper.delete(wrapper);

        userDTO.getRoles().forEach(roleDO -> {
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setUserId(userDO.getId());
            userRoleDO.setRoleId(roleDO.getId());
            userRoleMapper.insert(userRoleDO);
        });
    }


    @Transactional
    @Override
    public void delete(Long... ids) {
        QueryWrapper<UserRoleDO> wrapper = new QueryWrapper<>();
        wrapper.in("user_id", ids);
        userRoleMapper.delete(wrapper);

        userMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public UserDO getByUsername(String username) {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }


    @Override
    public UserDO validate(String username, String password) {
        UserDO userDO = getByUsername(username);
        if (userDO == null) {
            throw new MessageException(GlobalConstant.USER_NOT_EXIST_CODE);
        }

        if (!BCrypt.checkpw(password, userDO.getPassword())) {
            throw new MessageException(GlobalConstant.USER_PASSWORD_ERROR_CODE);
        }

        if (UserStatus.DISABLED.getCode().equals(userDO.getStatus())) {
            throw new MessageException(GlobalConstant.USER_DISABLED_CODE);
        }
        return userDO;
    }

    @Override
    public Set<String> getMenuPermissions(String systemKey, Long userId) {
        QueryWrapper<UserRoleDO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);

        List<UserRoleDO> userRoleDOList = userRoleMapper.selectList(wrapper);
        Long[] roleIds = userRoleDOList.stream().map(UserRoleDO::getRoleId).toArray(Long[]::new);
        return roleService.getMenuResources(systemKey, roleIds)
                .stream().map(MenuResourceDO::getPermission).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getMenuPermissions(Long systemId, Long userId) {
        QueryWrapper<UserRoleDO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);

        List<UserRoleDO> userRoleDOList = userRoleMapper.selectList(wrapper);
        Long[] roleIds = userRoleDOList.stream().map(UserRoleDO::getRoleId).toArray(Long[]::new);
        return roleService.getMenuResources(systemId, roleIds)
                .stream().map(MenuResourceDO::getPermission).collect(Collectors.toSet());
    }

}
