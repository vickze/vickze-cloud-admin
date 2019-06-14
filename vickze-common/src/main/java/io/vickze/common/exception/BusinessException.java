package io.vickze.common.exception;

public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, String code) {
        super(msg);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
