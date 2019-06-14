package io.vickze.common.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.Data;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-06-04 14:43
 */
public class ClientException extends HystrixBadRequestException {


    public ClientException(String message) {
        super(message);
    }

    @Data
    public static class ExceptionInfo {

        private String exceptionClass;

        private String message;
    }

}

