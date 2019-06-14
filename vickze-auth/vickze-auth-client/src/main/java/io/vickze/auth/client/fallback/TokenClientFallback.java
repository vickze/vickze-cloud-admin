package io.vickze.auth.client.fallback;

import io.vickze.auth.client.TokenClient;
import io.vickze.auth.domain.DTO.AuthUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-10 15:34
 */
@Slf4j
@Service
public class TokenClientFallback implements TokenClient {
    @Override
    public AuthUserDTO validate(String token) {
        log.error("TokenClient validate fallback.");
        return null;
    }
}
