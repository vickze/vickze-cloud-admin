package io.vickze.auth.exception;

import io.vickze.auth.constant.GlobalConstant;
import io.vickze.common.exception.ClientException;

public class UnauthorizedException extends ClientException {

    public UnauthorizedException() {
        super(GlobalConstant.UNAUTHORIZED_CODE);
    }

    public UnauthorizedException(boolean serializeExceptionClass) {
        super(GlobalConstant.UNAUTHORIZED_CODE, serializeExceptionClass);
    }

    public UnauthorizedException(String message) {
        super(message);
    }


    public UnauthorizedException(String message, boolean serializeExceptionClass) {
        super(message, serializeExceptionClass);
    }
}
