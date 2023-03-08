package ru.miroshka.market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.miroshka.market.api.dto.StringResponse;
import ru.miroshka.market.api.models.CartDto;
import ru.miroshka.market.carts.converters.CartConverter;

import ru.miroshka.market.carts.servicies.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;


    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CartDto getCurrentCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid) {
        String targetUuid = getCartUuid(username,uuid);
        return cartConverter.entityToDto(this.cartService.getCurrentCart(targetUuid));
    }

    @GetMapping("/{uuid}/add/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CartDto putProductToCast(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        String targetUuid = getCartUuid(username,uuid);
        this.cartService.putProductToCart(targetUuid, id);
        return cartConverter.entityToDto(this.cartService.getCurrentCart(targetUuid));
    }

    @GetMapping("/{uuid}/delete/{id}")
    public void deleteProductBasket(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        String targetUuid = getCartUuid(username,uuid);
        this.cartService.delProductCartById(targetUuid, id);
    }

    @GetMapping("/{uuid}/delete")
    public void deleteAllProductBasket(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid) {
        String targetUuid = getCartUuid(username,uuid);
        this.cartService.delAllProductBasket(targetUuid);
    }

    @GetMapping("/{uuid}/change")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CartDto putProductToCast(
            @RequestParam(name = "productId", defaultValue = "0") Long id,
            @RequestParam(name = "count", defaultValue = "0") int count,
            @RequestHeader(name = "username", required = false) String username,
            @PathVariable String uuid) {

        String targetUuid = getCartUuid(username,uuid);
        if (count == 1) {
            this.cartService.putProductToCart(targetUuid, id);
        } else if (count == -1) {
            this.cartService.delProductCartOneById(targetUuid, id);
        }
        return cartConverter.entityToDto(this.cartService.getCurrentCart(targetUuid));
    }

    @GetMapping("/generate_uuid")
    public StringResponse generateUuid(){
        return new StringResponse(UUID.randomUUID().toString());
    }

    private String getCartUuid(String username, String uuid) {
        if (username != null) {
            return username;
        }

        return uuid;
    }

//@PathVariable String uuid, @PathVariable Long id
    @GetMapping("/reload_carts/{uuid}/{userid}")
    public void reloadCarts(@PathVariable String uuid, @PathVariable String userid){
        if (!uuid.isEmpty()&&!userid.isEmpty()){
            this.cartService.reloadCarts(uuid,userid);
        }
    }


}
