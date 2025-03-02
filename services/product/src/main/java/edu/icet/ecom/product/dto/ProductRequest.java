package edu.icet.ecom.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        @NotNull(message = "Product name is required")
        String name,
        @NotNull(message = "Product description is required")
        String description,
        @Positive(message = "Product price must be positive")
        BigDecimal price,
        @NotNull(message = "Product category is required")
        Integer categoryId,
        @Positive(message = "Available quantity must be positive")
        Double availableQuantity
) {
}
