package io.vickze.auth.enums;

import lombok.Getter;

public enum UserStatus {

    ENABLED(1, "启用"),
    DISABLED(0,"禁用");

    @Getter
    private Integer code;

    @Getter
    private String value;

    UserStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
