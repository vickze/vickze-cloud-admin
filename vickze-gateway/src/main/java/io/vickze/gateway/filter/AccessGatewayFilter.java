package io.vickze.gateway.filter;

import feign.FeignException;
import io.vickze.auth.client.CheckPermissionClient;
import io.vickze.auth.constant.GlobalConstant;
import io.vickze.auth.constant.TokenConstant;
import io.vickze.auth.domain.DTO.CheckPermissionDTO;
import io.vickze.common.domain.DTO.MessageResultDTO;
import io.vickze.auth.exception.ForbiddenException;
import io.vickze.auth.exception.UnauthorizedException;
import io.vickze.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-09 17:28
 */
@Configuration
@Slf4j
public class AccessGatewayFilter implements GlobalFilter {
    @Autowired
    private CheckPermissionClient checkPermissionClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();
        LinkedHashSet requiredAttribute = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        Iterator<URI> iterator = requiredAttribute.iterator();

        String requestUri = iterator.next().getPath();
        if (requestUri.endsWith("/")) {
            requestUri = requestUri.substring(0, requestUri.lastIndexOf("/"));
        }
        String method = request.getMethod().toString();

        String systemKey = null;
        List<String> systemHeader = request.getHeaders().get(GlobalConstant.SYSTEM_HEADER);
        if (CollectionUtils.isNotEmpty(systemHeader)) {
            systemKey = systemHeader.get(0);
        }
        //header里面为空
        if (StringUtils.isBlank(systemKey)) {
            //从url参数获取
            List<String> systemParam = request.getQueryParams().get(GlobalConstant.SYSTEM_HEADER);
            if (CollectionUtils.isNotEmpty(systemParam)) {
                systemKey = systemParam.get(0);
            }
        }

        String token = null;
        List<String> tokenHeader = request.getHeaders().get(TokenConstant.TOKEN_HEADER);
        if (CollectionUtils.isNotEmpty(tokenHeader)) {
            token = tokenHeader.get(0);
        }
        //header里面为空
        if (StringUtils.isBlank(token)) {
            //从url参数获取
            List<String> tokenParam = request.getQueryParams().get(TokenConstant.TOKEN_HEADER);
            if (CollectionUtils.isNotEmpty(tokenParam)) {
                token = tokenParam.get(0);
            }
        }

        CheckPermissionDTO checkPermissionDTO = new CheckPermissionDTO();
        checkPermissionDTO.setRequestUri(requestUri);
        checkPermissionDTO.setMethod(method);
        checkPermissionDTO.setSystemKey(systemKey);
        checkPermissionDTO.setToken(token);

        try {
            checkPermissionClient.checkPermission(checkPermissionDTO);
        } catch (FeignException e) {
            exchange.getResponse().setStatusCode(HttpStatus.valueOf(e.status()));
            return handleException(e, exchange);
        } catch (ForbiddenException e) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return handleException(e, exchange);
        } catch (UnauthorizedException e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return handleException(e, exchange);
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
            return handleException(e, exchange);
        }

        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private Mono<Void> handleException(Exception e, ServerWebExchange exchange) {
        MessageResultDTO messageResultDTO = new MessageResultDTO();
        messageResultDTO.setMessage(e.getMessage());
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(JsonUtil.toJson(messageResultDTO).getBytes());
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

}
