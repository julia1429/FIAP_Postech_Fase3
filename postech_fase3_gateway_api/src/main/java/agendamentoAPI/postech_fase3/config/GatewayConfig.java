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

        
            // TODO ANOTHER API
            /*
            .route("doctors", r -> r.path("/doctors/**")
                .uri("https://external-api-hospital.com"))
             */
            .build();
    }
}