package io.vickze.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
@ConfigurationProperties("token.uuid")
public class UUIDTokenProperties {

    private long expire;

    private boolean updateExpire;
}
