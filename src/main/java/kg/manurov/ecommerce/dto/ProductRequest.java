package kg.manurov.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        @NotNull
        String name,
        @NotNull
        String description,
        @NotNull
        @PositiveOrZero
        double availableQuantity,
        @NotNull
        @PositiveOrZero
        BigDecimal price,
        @NotNull
        @Positive
        Integer categoryId
){}
