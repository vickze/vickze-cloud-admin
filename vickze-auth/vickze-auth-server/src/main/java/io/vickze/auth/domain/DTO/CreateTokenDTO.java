package io.vickze.auth.domain.DTO;

import lombok.Data;

@Data
public class CreateTokenDTO {

    private String systemKey;

    private String username;

    private String password;

}
