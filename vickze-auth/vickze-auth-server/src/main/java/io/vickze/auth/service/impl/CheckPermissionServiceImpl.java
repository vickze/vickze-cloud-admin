package io.vickze.auth.service.impl;

import io.vickze.auth.domain.DO.SystemDO;
import io.vickze.auth.domain.DTO.AuthUserDTO;
import io.vickze.auth.domain.DTO.CheckPermissionDTO;
import io.vickze.auth.properties.AuthCheckPermissionProperties;
import io.vickze.auth.service.*;
import io.vickze.auth.exception.ForbiddenException;
import io.vickze.auth.exception.UnauthorizedException;
import io.vickze.common.domain.Interface;
import io.vickze.common.util.InterfaceUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-06-03 14:18
 */
@Service
public class CheckPermissionServiceImpl implements CheckPermissionService {
    @Autowired
    private SystemService systemService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuResourceService menuResourceService;

    @Autowired
    private AuthCheckPermissionProperties authCheckPermissionProperties;

    @Override
    public void checkPermission(CheckPermissionDTO checkPermissionDTO) {
        // 请求接口
        Interface requestInterface = new Interface(checkPermissionDTO.getMethod(), checkPermissionDTO.getRequestUri());

        if (InterfaceUtil.interfaceContains(requestInterface, authCheckPermissionProperties.getIgnoreInterfaces())) {
            return;
        }
        if (StringUtils.isBlank(checkPermissionDTO.getSystemKey())) {
            throw new ForbiddenException();
        }
        SystemDO systemDO = systemService.selectByKey(checkPermissionDTO.getSystemKey());
        if (systemDO == null) {
            throw new ForbiddenException();
        }

        if (InterfaceUtil.interfaceContains(requestInterface, authCheckPermissionProperties.getIgnoreInterfacesWithoutLogin())) {
            return;
        }

        //接口需要登录
        if (StringUtils.isBlank(checkPermissionDTO.getToken())) {
            throw new UnauthorizedException();
        }
        AuthUserDTO authUserDTO = tokenService.validate(checkPermissionDTO.getToken());
        if (authUserDTO == null) {
            throw new UnauthorizedException();
        }

        Set<String> userPermissions = userService.getMenuPermissions(systemDO.getId(), authUserDTO.getUserId());
        //是否允许无菜单资源权限登录
        if (Boolean.FALSE.equals(systemDO.getNotResourceLogin()) && CollectionUtils.isEmpty(userPermissions)) {
            throw new UnauthorizedException();
        }

        //一个接口可能对应多个菜单功能，比如 按钮详情->详情接口 按钮编辑->详情接口、更新接口
        //详情接口同时对应两个功能，用户只需要拥有其中一个功能权限即可访问该接口
        //注意像/menu/tree未设置权限，/menu/{id}设置了权限的话，/menu/tree会跟/menu/{id}匹配到
        List<String> permissions = menuResourceService.getPermissions(systemDO.getId(),
                checkPermissionDTO.getRequestUri(), checkPermissionDTO.getMethod());

        //判断用户是否有访问该菜单资源的权限
        if (!hasPermission(userPermissions, permissions)) {
            throw new UnauthorizedException();
        }
    }

    private boolean hasPermission(Set<String> userPermissions, List<String> permissions) {
        if (CollectionUtils.isEmpty(permissions)) {
            return true;
        }
        for (String permission : permissions) {
            if (userPermissions.contains(permission)) {
                return true;
            }
        }
        return false;
    }
}
