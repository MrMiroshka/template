package ru.miroshka.market.api.dto;


public class StringResponse {
    private String value;

    public StringResponse(String name) {
        this.value = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
