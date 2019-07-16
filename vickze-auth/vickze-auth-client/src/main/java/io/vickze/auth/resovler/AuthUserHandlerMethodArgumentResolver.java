package io.vickze.auth.resovler;


import io.vickze.auth.client.TokenClient;
import io.vickze.auth.constant.TokenConstant;
import io.vickze.auth.domain.DTO.AuthUserDTO;
import io.vickze.auth.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @date 2017-12-12 15:41
 */
@Component
@Slf4j
public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private TokenClient tokenClient;

    public AuthUserHandlerMethodArgumentResolver() {
        log.debug("AuthUserHandlerMethodArgumentResolver init.");
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(AuthUserDTO.class)
                && methodParameter.hasParameterAnnotation(AuthUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        //header
        String token = nativeWebRequest.getHeader(TokenConstant.TOKEN_HEADER);
        if (StringUtils.isBlank(token)) {
            //url
            token = nativeWebRequest.getParameter(TokenConstant.TOKEN_HEADER);
        }
        if (StringUtils.isBlank(token)) {
            throw new UnauthorizedException(false);
        }

        return tokenClient.validate(token);
    }
}
