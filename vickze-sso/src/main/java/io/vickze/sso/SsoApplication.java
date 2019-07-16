package io.vickze.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-07-11 11:15
 */
@EnableDiscoveryClient
@ComponentScan(basePackages = "io.vickze")
@SpringBootApplication
@EnableFeignClients({"io.vickze.auth.client", "io.vickze.auth.client.fallback"})
public class SsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }

}
