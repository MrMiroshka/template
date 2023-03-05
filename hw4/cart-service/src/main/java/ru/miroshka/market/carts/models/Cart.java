package ru.miroshka.market.carts.models;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.miroshka.market.api.dto.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Data
public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

/*    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }*/

/*    public void addListCartItem(List<CartItem> productsList) {
        for (CartItem cartItem: productsList) {
            this.items.add(cartItem);
        }
    }*/

    public void addListProduct(ProductDto product) {
        for (CartItem item : items) {
            if (item.getProductId().equals(product.getId())) {
                item.changeQuantity(1);
                recalculate();
                return;
            }
        }
        this.items.add(new CartItem(product.getId(), product.getTitle(), 1,
                product.getCost(), product.getCost()));
        recalculate();
    }

    private void recalculate() {
        totalPrice = new BigDecimal("0.0");
        for (CartItem item : items) {
            totalPrice = totalPrice.add(item.getPrice());
        }
    }

    /**
     * Удаляет первый попавшийся продукт в корзине с заданным id
     **/
    public void deleteById(Long id) {
        items.removeIf(n -> (n.getProductId().equals(id)));
        recalculate();
    }

    /**
     * Удаляет 1 штуку из группы товаров.
     * Если на остатке 1 продукт то удаляю товар в корзине с заданным id
     **/
    public void deleteOneById(Long id) {
        for (CartItem p : items) {
            if (p.getProductId().equals(id)) {
                if (p.getQuantity() == 1) {
                    items.remove(p);
                } else if (p.getQuantity() > 1) {
                    p.changeQuantity(-1);
                }
                recalculate();
                return;
            }
        }
    }

    /**
     * Удаляет все содержимое корзины
     */
    public void deleteByAll() {
        items.clear();
        recalculate();
    }
}
