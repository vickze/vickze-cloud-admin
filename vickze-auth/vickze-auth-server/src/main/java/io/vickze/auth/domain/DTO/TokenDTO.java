package io.vickze.auth.domain.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class TokenDTO {

    private String token;

    private long expire;

    private Set<String> permissions;
}
