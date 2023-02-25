package ru.miroshka.market.api.models;

import java.math.BigDecimal;
import java.util.List;


public class CartDto {
    private List<CartDtoItem> items;
    private BigDecimal totalPrice;

    public List<CartDtoItem> getItems() {
        return items;
    }

    public void setItems(List<CartDtoItem> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
