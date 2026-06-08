package com.esmt.gateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    //liste des routes sans token like login
    //ce sont les routes aux quelles on peut acceder sans avoir besoin de token
    public static final List<String> openApiEndpoints = List.of(
            "/api/auth/login",
            "/api/users/verify",
            "/api/users/exists",
            "/api/auth/update-password",
            "v3/api-docs",
            "swagger-ui",
            "swagger-config",
            "webjars"
    );

    /*public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));*/
    public Predicate<ServerHttpRequest> isSecured = request -> {
        String path = request.getURI().getPath();

        // Si l'URL contient un des mots-clés publics, on renvoie false (Route NON sécurisée -> Accès libre)
        return openApiEndpoints.stream().noneMatch(path::contains);
    };
}
