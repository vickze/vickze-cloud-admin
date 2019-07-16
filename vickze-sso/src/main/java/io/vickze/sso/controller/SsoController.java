package io.vickze.sso.controller;

import io.vickze.auth.client.TokenClient;
import io.vickze.auth.client.UserClient;
import io.vickze.auth.constant.GlobalConstant;
import io.vickze.auth.constant.TokenConstant;
import io.vickze.auth.domain.DTO.AuthUserDTO;
import io.vickze.auth.exception.ForbiddenException;
import io.vickze.auth.exception.UnauthorizedException;
import io.vickze.common.exception.MessageException;
import io.vickze.sso.properties.SsoProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-07-11 11:21
 */
@RestController
public class SsoController {
    @Autowired
    private TokenClient tokenClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private SsoProperties ssoProperties;


    @GetMapping("/validateService")
    public void validateService(@RequestParam String systemKey,
                                @RequestParam String service, HttpServletResponse response) throws MalformedURLException {
        URL url = new URL(service);
        String serviceOrigin = url.getProtocol() + "://" + url.getAuthority();
        //可信任的地址列表
        List<String> trustList = ssoProperties.getSystem().get(systemKey);
        if (trustList != null) {
            for (String trust : trustList) {
                if (trust.endsWith("/")) {
                    trust = trust.substring(0, trust.lastIndexOf("/"));
                }
                if (trust.equals(serviceOrigin)) {
                    //允许访问
                    return;
                }
            }
        }

        //不允许访问
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }

    @GetMapping("/validateToken")
    public AuthUserDTO validateToken(@RequestHeader(GlobalConstant.SYSTEM_HEADER) String systemKey,
                              @RequestHeader(TokenConstant.TOKEN_HEADER) String token, HttpServletResponse response) {
        if (StringUtils.isBlank(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        AuthUserDTO authUserDTO = tokenClient.validate(token);
        if (authUserDTO == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        try {
            userClient.checkSystemAccess(systemKey, authUserDTO.getUserId());
        } catch (ForbiddenException e) {
            throw new ForbiddenException(e.getMessage(), false);
        } catch (UnauthorizedException e) {
            throw new MessageException(GlobalConstant.SYSTEM_NOT_RESOURCE_CODE);
        }
        return authUserDTO;
    }
}
