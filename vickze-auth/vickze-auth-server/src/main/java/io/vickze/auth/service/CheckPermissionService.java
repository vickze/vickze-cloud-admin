package io.vickze.auth.service;

import io.vickze.auth.domain.DTO.CheckPermissionDTO;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-06-03 14:17
 */
public interface CheckPermissionService {

    void checkPermission(CheckPermissionDTO checkPermissionDTO);
}
