package com.canvasbids.gateway.configurations;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    List<String> openRoutes = List.of(
            "/user/register",
            "user/login"
    );
    public Predicate<ServerHttpRequest> isSecure =
            serverHttpRequest -> openRoutes.stream().noneMatch(uri-> serverHttpRequest.getURI().getPath().contains(uri));
}
