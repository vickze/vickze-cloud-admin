package io.vickze.gateway.properties;

import io.vickze.common.domain.Interface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-08-14 11:42
 */
@Data
@RefreshScope
@ConfigurationProperties(value = "access", ignoreUnknownFields = false)
@Component
public class AccessForbiddenProperties {

    private List<Interface> forbiddenInterfaces = new ArrayList<>();

}

