package ru.miroshka.market.carts.converters;

import org.springframework.stereotype.Component;
import ru.miroshka.market.api.models.CartDtoItem;
import ru.miroshka.market.carts.models.CartItem;

@Component
public class CartItemConverter {
    public CartDtoItem entityToDto(CartItem cartItem){
        CartDtoItem cartDtoItem = new CartDtoItem();
        cartDtoItem.setPrice(cartItem.getPrice());
        cartDtoItem.setPricePerProduct(cartItem.getPricePerProduct());
        cartDtoItem.setQuantity(cartItem.getQuantity());
        cartDtoItem.setProductTitle(cartItem.getProductTitle());
        cartDtoItem.setProductId(cartItem.getProductId());
        return cartDtoItem;
    }
}
