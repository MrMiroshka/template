package ru.miroshka.market.core.listeners;

import org.springframework.stereotype.Service;
import ru.miroshka.market.core.servicies.OrderService;

@Service

public class LogSoutListener {
    private final OrderService orderService;

    public LogSoutListener(OrderService orderService) {
        this.orderService = orderService;
        orderService.attach(new LogOrders());
    }
}
