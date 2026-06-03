package com.esmt.gateway.filter;

import com.esmt.gateway.config.RouteValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {
    //Dans cette classe, on verifie les token voir s'il est valide
    @Autowired
    private RouteValidator validator;
    /*@Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if ("OPTIONS".equals(request.getMethod().name())) {
            return chain.filter(exchange);
        }

        //est-ce que la route est sécurisée ? verif token
        if (validator.isSecured.test(request)) {
            log.info("[GATEWAY] Vérification du token pour : {}", request.getURI().getPath());

            //est-ce que le header Authorization existe ?
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                log.error("[GATEWAY] Header Authorization manquant !");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // validation token
            String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            log.info("[GATEWAY] Token reçu : {}", authHeader);
        }

        return chain.filter(exchange);
    }*/
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 1. Autoriser systématiquement les OPTIONS (Preflight CORS)
        if ("OPTIONS".equals(request.getMethod().name())) {
            return chain.filter(exchange);
        }

        if (validator.isSecured.test(request)) {
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                log.error("[GATEWAY] Header Authorization manquant !");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // Logique de validation du token ici...
            // Si tout est OK :
            return chain.filter(exchange);
        }

        // Si la route n'est pas sécurisée, on laisse passer
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return -1;
    }

}
