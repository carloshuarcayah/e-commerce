package pe.com.birdcare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotNull(message = "Category ID is required")
        Long categoryId,

        @NotBlank(message = "Product name is required")
        @Size(max = 100)
        String name,

        String description,

        @NotNull(message = "Price is required")
        @PositiveOrZero(message = "Price cannot be negative")
        BigDecimal price,

        @NotNull(message = "Stock is required")
        @PositiveOrZero(message = "Stock cannot be negative")
        Integer stock
) {
}
