package io.vickze.auth.exception;


import io.vickze.common.domain.DTO.BusinessResultDTO;
import io.vickze.common.domain.DTO.MessageResultDTO;
import io.vickze.common.exception.BusinessException;
import io.vickze.common.exception.FeignExceptionErrorDecoder;
import io.vickze.common.exception.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2017-09-08 22:07
 */
@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler {

    public AuthExceptionHandler() {
        log.debug("AuthExceptionHandler init.");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public FeignExceptionErrorDecoder.ExceptionInfo handleUnauthorizedException(UnauthorizedException e) {
        FeignExceptionErrorDecoder.ExceptionInfo exceptionInfo = new FeignExceptionErrorDecoder.ExceptionInfo();
        exceptionInfo.setExceptionClass(e.getClass().getName());
        exceptionInfo.setMessage(e.getMessage());
        return exceptionInfo;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public FeignExceptionErrorDecoder.ExceptionInfo handleForbiddenException(ForbiddenException e) {
        FeignExceptionErrorDecoder.ExceptionInfo exceptionInfo = new FeignExceptionErrorDecoder.ExceptionInfo();
        exceptionInfo.setExceptionClass(e.getClass().getName());
        exceptionInfo.setMessage(e.getMessage());
        return exceptionInfo;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MessageException.class)
    public MessageResultDTO handleMsgException(MessageException e) {
        MessageResultDTO messageResultDTO = new MessageResultDTO();
        messageResultDTO.setMessage(e.getMessage());
        return messageResultDTO;
    }


    @ExceptionHandler(BusinessException.class)
    public BusinessResultDTO handleBusinessException(BusinessException e) {
        BusinessResultDTO businessResultDTO = new BusinessResultDTO();
        businessResultDTO.setCode(e.getCode());
        businessResultDTO.setMsg(e.getMessage());
        return businessResultDTO;
    }
}
