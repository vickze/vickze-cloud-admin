package io.vickze.auth.client;

import io.vickze.auth.client.fallback.UserClientFallback;
import io.vickze.auth.domain.DTO.CheckPermissionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-09 17:39
 */
@FeignClient(value = "vickze-auth", path="/user", fallback = UserClientFallback.class)
public interface UserClient {

    @GetMapping("/systemAccess")
    void checkSystemAccess(@RequestParam String systemKey, @RequestParam Long userId);

}
