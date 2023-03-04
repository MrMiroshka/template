package ru.miroshka.market.carts.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.miroshka.market.api.dto.ProductDto;
import ru.miroshka.market.api.exceptions.ResourceNotFoundException;


import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ProductServiceIntegration {
    @Value("${ProductServiceIntegration-getProductById}")
    private String url;

    private final WebClient productServiceWebClient;
    /* private final RestTemplate restTemplate;*/

    public Optional<ProductDto> getProductById(Long id) {

        Optional<ProductDto> productDto = Optional.ofNullable(productServiceWebClient.get()
                .uri("/api/v1/products/" + id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        //clientResponse -> clientResponse.bodyToMono(AppError.class).map(ae -> new ResourceNotFoundException(ae.getMessage()))
                        clientResponse -> Mono.error(new ResourceNotFoundException("Товар не найден в продуктовом МС"))
                )
                .bodyToMono(ProductDto.class)
                .block()
        );

        return productDto;

/*        Optional<ProductDto> productDto = Optional.ofNullable(restTemplate.getForObject(
                url+id,
                ProductDto.class));
        return productDto;*/
    }


/*    public void clearUserCart(String username){
        cartServiceWebclient.get()
                .uri("/api/v1/cart/0/clear")
                .header("username",username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public CartDto getUserCart(String username){
        CartDto cart = cartServiceWebclient.get()
                .uri("/api/v1/cart/0")
                .header("username",username)
                //.bodyValue(body) //for POST
                .retrieve()
                .onStatus(
                        httpStatus ->  httpStatus.is4xxClientError(), // HttpStatus::is4xxClientError
                        clientResponse -> clientResponse.bodyToMono(CartServiceAppError.class).map(
                                body->{
                                    if (body.getCode().equals(CartServiceAppError.CartServiceError.CART_NOT_FOUND.name())){
                                        return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзины: корзина не найдена");
                                    }
                                    if (body.getCode().equals(CartServiceAppError.CartServiceError.CART_IS_BROKEN.name())){
                                        return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзины: корзина сломана");
                                    }
                                    return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзины: причина не известна");
                                }
                        )
                )
             //   .onStatus(HttpStatus::is4xxClientError.clientResponse -> Mono.error(new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзины")))
            //    .onStatus(HttpStatus::is4xxClientError.clientResponse -> Mono.error(new CartServiceIntegrationException("Сервис корзины сломался")))
                .bodyToMono(CartDto.class)
                .block();

                                    return cart;
    }*/

}
