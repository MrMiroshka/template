package ru.miroshka.market.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.miroshka.market.core.data.OrderItem;


public interface OrderItemsDao extends JpaRepository<OrderItem, Long> {
}
