package io.vickze.auth.client.fallback;

import io.vickze.auth.client.CheckPermissionClient;
import io.vickze.auth.domain.DTO.CheckPermissionDTO;
import io.vickze.common.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-06-03 14:56
 */
@Slf4j
@Service
public class CheckPermissionClientFallback implements CheckPermissionClient {
    @Override
    public void checkPermission(CheckPermissionDTO checkPermissionDTO) {
        log.error("CheckPermissionClientFallback checkPermission fallback.");
        //抛出异常使fallback失败
        throw new ClientException("CheckPermissionClientFallback checkPermission fallback fail.");
    }
}
