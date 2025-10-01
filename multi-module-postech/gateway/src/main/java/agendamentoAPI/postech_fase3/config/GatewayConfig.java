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

            // Rota para agendamentos
            .route("appointments", r -> r.path("/appointments/**")
                .filters(f -> f.rewritePath("/appointments/(?<path>.*)", "/${path}"))
                
                .uri("http://localhost:8081"))


            // Rota para histórico
            .route("history", r -> r.path("/history/**")
                .filters(f -> f.rewritePath("/history/(?<path>.*)", "/${path}"))

                .uri("http://localhost:8082"))

            .build();
    }
}