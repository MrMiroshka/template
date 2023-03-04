package ru.miroshka.market.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDto {

    private final Long id;
    private final String phone;
    private final BigDecimal totalPrice;
    private final String address;
    private final LocalDateTime createdAt;

    public static class Builder {
        private final Long id;
        private String phone;
        private final BigDecimal totalPrice;
        private String address;
        private final LocalDateTime createdAt;

        public Builder(Long id, BigDecimal totalPrice, LocalDateTime createdAt) {
            this.id = id;
            this.totalPrice = totalPrice;
            this.createdAt = createdAt;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public OrderDto buidl() {
            return new OrderDto(this);
        }
    }


    private OrderDto(OrderDto.Builder builder) {
        this.id = builder.id;
        this.totalPrice = builder.totalPrice;
        this.createdAt = builder.createdAt;
        this.phone= builder.phone;
        this.address = builder.address;
    }

    public Long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
/*    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrderDto(Long id, String phone, BigDecimal totalPrice, String address, LocalDateTime createdAt) {
        this.id = id;
        this.phone = phone;
        this.totalPrice = totalPrice;
        this.address = address;
        this.createdAt = createdAt;
    }*/


}
