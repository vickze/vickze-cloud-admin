package io.vickze.auth.client;

import io.vickze.auth.client.fallback.TokenClientFallback;
import io.vickze.auth.constant.GlobalConstant;
import io.vickze.auth.domain.DTO.AuthUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-10 15:02
 */
@FeignClient(value = "vickze-auth", path="/token", fallback = TokenClientFallback.class)
public interface TokenClient {

    @GetMapping("/validate")
    AuthUserDTO validate(@RequestParam String token);
}
