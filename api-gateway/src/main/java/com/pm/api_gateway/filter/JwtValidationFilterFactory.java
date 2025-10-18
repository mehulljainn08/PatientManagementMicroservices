package com.pm.api_gateway.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JwtValidationFilterFactory extends AbstractGatewayFilterFactory<Object> {


    private final WebClient webClient;

    public JwtValidationFilterFactory(WebClient.Builder webClientBuilder
    ,@Value("${auth.service.url}") String authServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config) {

        return (exchange, chain) -> {
            String jwtToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if(jwtToken == null || !jwtToken.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            jwtToken = jwtToken.substring(7);

            return webClient.get()
                    .uri("/validate")
                    .header(HttpHeaders.AUTHORIZATION, jwtToken)
                    .retrieve()
                    .toBodilessEntity()
                    .then(chain.filter(exchange));


        }
    }
}
