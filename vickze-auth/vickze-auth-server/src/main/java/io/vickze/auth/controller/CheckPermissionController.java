package io.vickze.auth.controller;

import io.vickze.auth.client.CheckPermissionClient;
import io.vickze.auth.domain.DTO.CheckPermissionDTO;
import io.vickze.auth.service.CheckPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-06-03 14:55
 */
@RestController
public class CheckPermissionController implements CheckPermissionClient {
    @Autowired
    private CheckPermissionService checkPermissionService;

    @Override
    public void checkPermission(CheckPermissionDTO checkPermissionDTO) {
        checkPermissionService.checkPermission(checkPermissionDTO);
    }
}
