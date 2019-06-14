package io.vickze.auth.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.vickze.auth.constant.GlobalConstant;
import io.vickze.auth.domain.DO.SystemDO;
import io.vickze.auth.domain.DO.UserDO;
import io.vickze.auth.domain.DTO.AuthUserDTO;
import io.vickze.auth.domain.DTO.CreateTokenDTO;
import io.vickze.auth.domain.DTO.TokenDTO;
import io.vickze.auth.properties.JwtProperties;
import io.vickze.auth.service.SystemService;
import io.vickze.auth.service.TokenService;
import io.vickze.auth.service.UserService;
import io.vickze.auth.exception.ForbiddenException;
import io.vickze.common.exception.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.*;
import java.util.Date;
import java.util.Set;

@ConditionalOnProperty(
        name = {"token.jwt.enabled"},
        matchIfMissing = true
)
@Slf4j
@Service
public class JwtServiceImpl implements TokenService, InitializingBean {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserService userService;
    @Autowired
    private SystemService systemService;

    private PublicKey publicKey;

    private PrivateKey privateKey;

    private static final String USERNAME = "username";


    //or @PostConstruct
    public void afterPropertiesSet() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(jwtProperties.getSecret().getBytes());
        keyPairGenerator.initialize(2048, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    @Override
    public TokenDTO create(CreateTokenDTO createTokenDTO) {
        if (StringUtils.isBlank(createTokenDTO.getSystemKey())) {
            throw new ForbiddenException();
        }
        SystemDO systemDO = systemService.selectByKey(createTokenDTO.getSystemKey());
        if (systemDO == null) {
            throw new ForbiddenException();
        }

        UserDO userDO = userService.validate(createTokenDTO.getUsername(), createTokenDTO.getPassword());
        Set<String> permissions = userService.getMenuPermissions(systemDO.getId(), userDO.getId());

        //是否允许无菜单资源权限登录
        if (Boolean.FALSE.equals(systemDO.getNotResourceLogin()) && CollectionUtils.isEmpty(permissions)) {
            throw new MessageException(GlobalConstant.SYSTEM_NOT_RESOURCE_CODE);
        }

        Date now = new Date();
        String jws = Jwts.builder()
                .setSubject(String.valueOf(userDO.getId()))
                .claim(USERNAME, userDO.getUsername())
                .setIssuedAt(now)
                .setExpiration(DateUtils.addSeconds(now, (int) jwtProperties.getExpire()))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setExpire(jwtProperties.getExpire());
        tokenDTO.setToken(jws);
        tokenDTO.setPermissions(permissions);
        return tokenDTO;
    }

    @Override
    public AuthUserDTO validate(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
            //返回
            AuthUserDTO authUserDTO = new AuthUserDTO();
            authUserDTO.setUserId(Long.valueOf(claims.getSubject()));
            authUserDTO.setUsername(claims.get(USERNAME).toString());
            return authUserDTO;
        } catch (Exception e) {
            log.info("无效token:{}", token);
        }

        return null;
    }

    @Override
    public void delete(String token) {
        //do nothing jwt应该是前端丢弃token
    }

}
