package io.vickze.common.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.Data;
import lombok.Getter;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-06-04 14:43
 */
public class ClientException extends HystrixBadRequestException {

    @Getter
    private boolean serializeExceptionClass;

    public ClientException(String message) {
        super(message);
        this.serializeExceptionClass = true;
    }

    public ClientException(String message, boolean serializeExceptionClass) {
        super(message);
        this.serializeExceptionClass = serializeExceptionClass;
    }
}

