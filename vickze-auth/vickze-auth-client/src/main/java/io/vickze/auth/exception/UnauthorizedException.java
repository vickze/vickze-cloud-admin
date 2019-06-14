package io.vickze.auth.exception;

import io.vickze.auth.constant.GlobalConstant;
import io.vickze.common.exception.ClientException;

public class UnauthorizedException extends ClientException {

    public UnauthorizedException() {
        super(GlobalConstant.UNAUTHORIZED_CODE);
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
