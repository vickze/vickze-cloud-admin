package io.vickze.auth.client.fallback;

import io.vickze.auth.client.UserClient;
import io.vickze.common.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-09 17:46
 */
@Slf4j
@Service
public class UserClientFallback implements UserClient {

    @Override
    public void checkSystemAccess(String systemKey, Long userId) {
        log.error("UserClient checkSystemAccess fallback.");
        //抛出异常使fallback失败
        throw new ClientException("UserClient checkSystemAccess fallback fail.");
    }
}
