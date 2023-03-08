package ru.miroshka.market.carts.servicies;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.miroshka.market.api.dto.ProductDto;
import ru.miroshka.market.api.exceptions.ResourceNotFoundException;
import ru.miroshka.market.carts.integrations.ProductServiceIntegration;
import ru.miroshka.market.carts.models.Cart;
import ru.miroshka.market.carts.models.CartItem;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;
    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;

    public Cart getCurrentCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (!redisTemplate.hasKey(targetUuid)) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(targetUuid);
    }

    public void  reloadCarts(String from,String to){
        Cart fromCart = this.getCurrentCart(from);
        Cart toCart = this.getCurrentCart(to);
        for (CartItem ci:fromCart.getItems()) {
            putProductToCart(to,ci.getProductId());
        }
        delAllProductBasket(from);
    }

/*    public CartService(@Lazy ProductServiceIntegration productServiceIntegration) {
        this.productServiceIntegration = productServiceIntegration;
    }*/

    public void delProductCartById(String uuid, Long id) {
        execute(uuid, cart -> cart.deleteById(id));
/*        Cart cart = getCurrentCart(uuid);
        cart.deleteById(id);
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);*/
    }

    public void delProductCartOneById(String uuid, Long id) {
        execute(uuid, cart -> cart.deleteOneById(id));
/*        Cart cart = getCurrentCart(uuid);
        cart.deleteOneById(id);
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);*/
    }

    public void delAllProductBasket(String uuid) {
        execute(uuid, Cart::deleteByAll);
/*        Cart cart = getCurrentCart(uuid);
        cart.deleteByAll();
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);*/
    }

    public List<CartItem> putProductToCart(String uuid, Long id) {
        ProductDto productPutToBasket = this.productServiceIntegration.getProductById(id).orElseThrow(() ->
                new ResourceNotFoundException("Такой продукт не найден id - " + id
                        + "! Не удается добавить продукт в корзину!"));
        Cart cart = getCurrentCart(uuid);
        cart.addListProduct(productPutToBasket);
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);
        return (getCurrentCart(uuid)).getItems();
    }


    private void execute(String uuid, Consumer<Cart> operation) {
        Cart cart = getCurrentCart(uuid);
        operation.accept(cart);
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);
    }

/*    public List<CartItem> getProductsFromBasket(String uuid) {
        return productCarts.get(uuid).getItems();
    }*/

}
