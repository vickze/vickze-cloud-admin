package io.vickze.auth.service;

import io.vickze.auth.domain.DTO.AuthUserDTO;
import io.vickze.auth.domain.DTO.CreateTokenDTO;
import io.vickze.auth.domain.DTO.TokenDTO;

public interface TokenService {

    TokenDTO create(CreateTokenDTO createTokenDTO);

    AuthUserDTO validate(String token);

    void delete(String token);
}
