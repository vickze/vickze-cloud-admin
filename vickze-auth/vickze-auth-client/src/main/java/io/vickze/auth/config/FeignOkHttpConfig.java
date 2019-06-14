package io.vickze.auth.config;

import feign.Feign;
import io.vickze.auth.properties.FeignOkhttpProperties;
import okhttp3.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@AutoConfigureBefore(FeignAutoConfiguration.class)
@Configuration
@ConditionalOnClass(Feign.class)
@EnableConfigurationProperties(FeignOkhttpProperties.class)
public class FeignOkHttpConfig {

	@Autowired
	private FeignOkhttpProperties feignOkhttpProperties;

	@Bean
	public okhttp3.OkHttpClient okHttpClient() {
		return new okhttp3.OkHttpClient.Builder()
				.readTimeout(feignOkhttpProperties.getReadTimeout(), TimeUnit.MILLISECONDS)
				.writeTimeout(feignOkhttpProperties.getWriteTimeout(), TimeUnit.MILLISECONDS)
				.connectTimeout(feignOkhttpProperties.getConnectionTimeout(), TimeUnit.MILLISECONDS)
				.connectionPool(new ConnectionPool(feignOkhttpProperties.getMaxConnections(), feignOkhttpProperties.getTimeToLive(), feignOkhttpProperties.getTimeToLiveUnit()))
				.followRedirects(feignOkhttpProperties.isFollowRedirects())
				.retryOnConnectionFailure(feignOkhttpProperties.isRetryOnConnectionFailure())
				.build();
	}
}
