package agendamentoAPI.postech_fase3.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

            .route("appointments", r -> r
                .path("/appointments/**")
                .filters(f -> f
                    .filter((exchange, chain) -> {
                        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
                        if (authHeader != null) {
                            exchange = exchange.mutate()
                                .request(req -> req.headers(h -> h.set("Authorization", authHeader)))
                                .build();
                        }
                        return chain.filter(exchange);
                    })
                )
                .uri("http://producer-app:8081"))

            .route("history", r -> r
                .path("/history/**")
                .filters(f -> f
                    .filter((exchange, chain) -> {
                        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
                        if (authHeader != null) {
                            exchange = exchange.mutate()
                                .request(req -> req.headers(h -> h.set("Authorization", authHeader)))
                                .build();
                        }
                        return chain.filter(exchange);
                    })
                )
                .uri("http://historico-app:8082"))

            .build();
    }
}
