package pe.com.birdcare.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        Boolean active,
        String categoryName
) {
}
