package io.vickze.sso.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-07-11 11:16
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("sso")
public class SsoProperties {

    private Map<String, List<String>> system;
}
