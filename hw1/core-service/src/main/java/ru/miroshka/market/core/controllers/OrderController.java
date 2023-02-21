package ru.miroshka.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.miroshka.market.api.dto.OrderDto;
import ru.miroshka.market.core.converters.OrderConverter;
import ru.miroshka.market.core.data.Order;
import ru.miroshka.market.core.servicies.OrderService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    //private final UserService userService;
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username/*,@RequestBody OrderData orderData*/) {
        //User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("Пользователь такой не найден"));
        orderService.createOrder(username);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<OrderDto> selectOrders(@RequestHeader String username) {
        //User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException());
        List<OrderDto> loDto = new ArrayList<>();

        for (Order order : orderService.selectOrders(username)) {
            loDto.add(orderConverter.entityToDto(order));
        }
        return loDto;
    }
}
