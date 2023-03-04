package ru.miroshka.market.core.servicies;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.miroshka.market.api.models.CartDtoItem;
import ru.miroshka.market.core.data.Order;
import ru.miroshka.market.core.data.OrderItem;
import ru.miroshka.market.core.repositories.OrderItemsDao;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemsDao orderItemsDao;
    private final ProductService productService;

    public void createOrderItem(CartDtoItem cartDtoItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(productService.findById(cartDtoItem.getProductId()).get());
        orderItem.setOrder(order);
        orderItem.setQuantity(cartDtoItem.getQuantity());
        orderItem.setPricePerProduct(cartDtoItem.getPricePerProduct());
        orderItem.setPrice(cartDtoItem.getPrice());
        orderItemsDao.save(orderItem);
    }
}
