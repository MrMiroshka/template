package ru.miroshka.market.auth.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.miroshka.market.api.dto.ProductDto;
import ru.miroshka.market.api.exceptions.ResourceNotFoundException;
import ru.miroshka.market.api.models.CartDto;


import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class CartServiceIntegration {

    private final WebClient cartServiceWebClient;


    public void reloadCarts(String from, String to) {

        cartServiceWebClient.get()
                .uri("api/v1/cart/reload_carts/"+from+"/"+to)
                .header("username",to)
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }


}
