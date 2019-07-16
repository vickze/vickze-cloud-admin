package io.vickze.auth.controller;

import io.vickze.auth.client.TokenClient;
import io.vickze.auth.constant.GlobalConstant;
import io.vickze.auth.constant.TokenConstant;
import io.vickze.auth.domain.DTO.AuthUserDTO;
import io.vickze.auth.domain.DTO.CreateTokenDTO;
import io.vickze.auth.domain.DTO.TokenDTO;
import io.vickze.auth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController implements TokenClient {

    @Autowired
    private TokenService tokenService;

    @Override
    public AuthUserDTO validate(String token) {
        return tokenService.validate(token);
    }

    @DeleteMapping
    public void delete(@RequestHeader(TokenConstant.TOKEN_HEADER) String token) {
        tokenService.delete(token);
    }
}
