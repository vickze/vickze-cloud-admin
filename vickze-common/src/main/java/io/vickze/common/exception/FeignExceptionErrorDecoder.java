package io.vickze.common.exception;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import io.vickze.common.util.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-06-04 11:00
 */
@Slf4j
@Component
public class FeignExceptionErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            if (response.body() != null) {
                ExceptionInfo exceptionInfo = JsonUtil.fromJson(Util.toString(response.body().asReader()), ExceptionInfo.class);
                Class clazz = Class.forName(exceptionInfo.getExceptionClass());
                return (Exception) clazz.getDeclaredConstructor(String.class).newInstance(exceptionInfo.getMessage());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return FeignException.errorStatus(methodKey, response);
    }

    @Data
    public static class ExceptionInfo {

        private String exceptionClass;

        private String message;
    }
}
