package io.vickze.auth.exception;

import io.vickze.auth.constant.GlobalConstant;
import io.vickze.common.exception.ClientException;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

public class ForbiddenException extends ClientException {

    public ForbiddenException() {
        super(GlobalConstant.FORBIDDEN_CODE);
    }

    public ForbiddenException(boolean serializeExceptionClass) {
        super(GlobalConstant.FORBIDDEN_CODE, serializeExceptionClass);
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, boolean serializeExceptionClass) {
        super(message, serializeExceptionClass);
    }
}
