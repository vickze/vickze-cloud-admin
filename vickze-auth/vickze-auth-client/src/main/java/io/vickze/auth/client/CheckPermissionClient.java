package io.vickze.auth.client;

import io.vickze.auth.client.fallback.CheckPermissionClientFallback;
import io.vickze.auth.domain.DTO.CheckPermissionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-06-03 14:56
 */
@FeignClient(value = "vickze-auth", fallback = CheckPermissionClientFallback.class)
public interface CheckPermissionClient {

    @PostMapping("/checkPermission")
    void checkPermission(@RequestBody CheckPermissionDTO checkPermissionDTO);
}
