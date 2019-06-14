package io.vickze.auth.exception;

import io.vickze.auth.constant.GlobalConstant;
import io.vickze.common.exception.ClientException;

public class ForbiddenException extends ClientException {

    public ForbiddenException() {
        super(GlobalConstant.FORBIDDEN_CODE);
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
