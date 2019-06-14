package io.vickze.auth.config;

import io.vickze.auth.resovler.AuthUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @date 2017-12-12 15:24
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private AuthUserHandlerMethodArgumentResolver authUserHandlerMethodArgumentResolver;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authUserHandlerMethodArgumentResolver);
    }
}