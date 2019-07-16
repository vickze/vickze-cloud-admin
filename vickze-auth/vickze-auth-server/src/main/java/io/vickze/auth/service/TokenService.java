package io.vickze.auth.service;

import io.vickze.auth.domain.DTO.AuthUserDTO;
import io.vickze.auth.domain.DTO.CreateTokenDTO;
import io.vickze.auth.domain.DTO.TokenDTO;

public interface TokenService {

    TokenDTO create(AuthUserDTO authUserDTO);

    AuthUserDTO validate(String token);

    void delete(String token);
}
