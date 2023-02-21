package ru.miroshka.market.core.validators;

import org.springframework.stereotype.Component;
import ru.miroshka.market.api.exceptions.ValidationException;
import ru.miroshka.market.api.dto.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {
    public void validate(ProductDto productDto) {
        List<String> errors = new ArrayList<>();
        if (productDto.getCost().compareTo(new BigDecimal("1"))<0) {
            errors.add("Цена продукта не может быть меньше 1");
        }

        if (productDto.getTitle().isBlank()) {
            errors.add("Продукт не может иметь пустое название");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
