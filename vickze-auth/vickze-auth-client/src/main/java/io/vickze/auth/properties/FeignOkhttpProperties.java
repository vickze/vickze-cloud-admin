package io.vickze.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.concurrent.TimeUnit;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-06-05 15:01
 */
@Data
@RefreshScope
@ConfigurationProperties("feign.okhttp")
public class FeignOkhttpProperties {


    private int readTimeout = 10000;
    private int writeTimeout = 10000;
    private int connectionTimeout = 2000;
    private int maxConnections = 200;
    private long timeToLive = 900L;
    private TimeUnit timeToLiveUnit = TimeUnit.SECONDS;
    private boolean followRedirects = true;
    private boolean retryOnConnectionFailure = true;

}
