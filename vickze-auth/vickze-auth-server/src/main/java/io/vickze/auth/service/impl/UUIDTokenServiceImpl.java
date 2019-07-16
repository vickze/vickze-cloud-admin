package io.vickze.auth.service.impl;

import io.vickze.auth.constant.GlobalConstant;
import io.vickze.auth.domain.DO.SystemDO;
import io.vickze.auth.domain.DO.UserDO;
import io.vickze.auth.domain.DTO.AuthUserDTO;
import io.vickze.auth.domain.DTO.CreateTokenDTO;
import io.vickze.auth.domain.DTO.TokenDTO;
import io.vickze.auth.exception.ForbiddenException;
import io.vickze.auth.properties.UUIDTokenProperties;
import io.vickze.auth.service.SystemService;
import io.vickze.auth.service.TokenService;
import io.vickze.auth.service.UserService;
import io.vickze.common.exception.MessageException;
import io.vickze.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.*;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ConditionalOnProperty(
        name = {"token.uuid.enabled"}
)
@Slf4j
@Service
public class UUIDTokenServiceImpl implements TokenService {

    @Autowired
    private UUIDTokenProperties uuidTokenProperties;
    @Autowired
    private UserService userService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private PublicKey publicKey;

    private PrivateKey privateKey;

    private static final String USERNAME = "username";

    @Override
    public TokenDTO create(AuthUserDTO authUserDTO) {
        String token = UUID.randomUUID().toString();
        String tokenKey = getTokenKey(token);

        stringRedisTemplate.opsForValue().set(tokenKey, JsonUtil.toJson(authUserDTO));
        stringRedisTemplate.expire(tokenKey, uuidTokenProperties.getExpire(), TimeUnit.SECONDS);
        log.info("userId:{} token:{}", authUserDTO.getUserId(), token);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setExpire(uuidTokenProperties.getExpire());
        tokenDTO.setToken(token);
        return tokenDTO;
    }

    @Override
    public AuthUserDTO validate(String token) {
        try {
            String tokenKey = getTokenKey(token);
            String json = stringRedisTemplate.opsForValue().get(tokenKey);
            if (StringUtils.isBlank(json)) {
                return null;
            }
            AuthUserDTO authUserDTO = JsonUtil.fromJson(json, AuthUserDTO.class);
            if (authUserDTO != null && uuidTokenProperties.isUpdateExpire()) {
                stringRedisTemplate.expire(tokenKey, uuidTokenProperties.getExpire(), TimeUnit.SECONDS);
            }
            return authUserDTO;
        } catch (Exception e) {
            log.info("无效token:{}", token);
        }

        return null;
    }

    @Override
    public void delete(String token) {
        stringRedisTemplate.delete(getTokenKey(token));
    }

    private String getTokenKey(String token) {
        return "TOKEN:" + token;
    }
}
