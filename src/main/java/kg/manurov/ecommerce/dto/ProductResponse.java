package kg.manurov.ecommerce.dto;

import kg.manurov.ecommerce.entity.Category;

import java.math.BigDecimal;

public record ProductResponse(
        Integer id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        Category category
){}
