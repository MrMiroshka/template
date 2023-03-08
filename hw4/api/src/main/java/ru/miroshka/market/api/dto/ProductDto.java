package ru.miroshka.market.api.dto;


import java.math.BigDecimal;

public class ProductDto {

    private  Long id;
    private  String title;
    private  BigDecimal cost;

    public ProductDto(){
    }

    public static class Builder {
        private final Long id;
        private final String title;
        private final BigDecimal cost;

        public Builder(Long id, String title, BigDecimal cost) {
            this.id = id;
            this.title = title;
            this.cost = cost;
        }

        public ProductDto buidl() {
            return new ProductDto(this);
        }
    }


    private ProductDto(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.cost = builder.cost;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return this.id;
    }

    public BigDecimal getCost() {
        return cost;
    }
}

