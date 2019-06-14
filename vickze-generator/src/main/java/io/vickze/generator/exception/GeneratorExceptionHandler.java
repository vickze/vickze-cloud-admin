package io.vickze.generator.exception;

import io.vickze.auth.exception.AuthExceptionHandler;
import io.vickze.auth.exception.UnauthorizedException;
import io.vickze.common.domain.DTO.MessageResultDTO;
import io.vickze.common.exception.FeignExceptionErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-06-14 11:28
 */
@RestControllerAdvice
@Slf4j
//继承test
public class GeneratorExceptionHandler extends AuthExceptionHandler {

    public GeneratorExceptionHandler() {
        log.debug("GeneratorExceptionHandler init.");
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public FeignExceptionErrorDecoder.ExceptionInfo handleUnauthorizedException(UnauthorizedException e) {
        log.debug("GeneratorExceptionHandler handleUnauthorizedException.");
        FeignExceptionErrorDecoder.ExceptionInfo exceptionInfo = new FeignExceptionErrorDecoder.ExceptionInfo();
        exceptionInfo.setExceptionClass(e.getClass().getName());
        exceptionInfo.setMessage(e.getMessage());
        return exceptionInfo;
    }
}
