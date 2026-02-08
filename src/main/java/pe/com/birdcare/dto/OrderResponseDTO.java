package pe.com.birdcare.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponseDTO(
        Long id,
        Long userId,
        String userName,
        LocalDateTime orderDate,
        BigDecimal total,
        String status,
        String shippingAddress,
        List<OrderItemResponseDTO> items
) {
}
