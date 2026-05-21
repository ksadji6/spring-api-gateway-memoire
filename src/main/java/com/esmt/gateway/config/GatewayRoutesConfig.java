package com.esmt.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        //Ici cest pour definir les routes à utiliser pour aller vers identity-service à partir du port de la gateway
        return builder.routes()
                //route vers identity-service
                .route("identity-service", r -> r
                        .path("/api/auth/**", "/api/users/**")
                        .filters(f -> f
                                .circuitBreaker(c -> c
                                        .setName("identityCB")
                                        .setFallbackUri("forward:/fallback/identity-service"))
                        )
                        .uri("lb://identity-service")
                )
                //route vers project_service
                .route("project-service", r -> r
                        .path("/api/projects/**", "/api/tasks/**", "/api/statistiques/**", "/api/projects/v3/api-docs")
                        .filters(f -> f
                                .circuitBreaker(c -> c
                                        .setName("projectCB")
                                        .setFallbackUri("forward:/fallback/project-service"))
                        )
                        .uri("lb://project-service")
                )
                .build();
    }
}