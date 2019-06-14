package io.vickze.auth.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-24 21:38
 */
@Data
@RefreshScope
@ConfigurationProperties(value = "auth.check-permission", ignoreUnknownFields = false)
@Component
public class AuthCheckPermissionProperties {

    private List<Interface> ignoreUris = new ArrayList<>();

    private List<Interface> ignoreUrisWithoutLogin = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Interface {
        private String method;

        private String uri;
    }
}

