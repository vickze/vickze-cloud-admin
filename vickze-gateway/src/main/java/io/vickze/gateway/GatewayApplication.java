package io.vickze.gateway;

import io.vickze.auth.exception.AuthExceptionHandler;
import io.vickze.auth.resovler.AuthUserHandlerMethodArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


/**
 * @author vick.zeng
 * date-time: 2018/11/19 15:02
 **/
@SpringCloudApplication
@ComponentScan(basePackages = "io.vickze", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {AuthExceptionHandler.class, AuthUserHandlerMethodArgumentResolver.class})
})
@EnableFeignClients({"io.vickze.auth.client", "io.vickze.auth.client.fallback"})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
