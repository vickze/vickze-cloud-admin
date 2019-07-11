package io.vickze.sso.controller;

import io.vickze.sso.properties.SsoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    private SsoProperties ssoProperties;


    @GetMapping("/validate")
    public void validate(@RequestParam String systemKey, @RequestParam String service, HttpServletResponse response) throws MalformedURLException {
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
                    return;
                }
            }
        }

        //不允许访问
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
