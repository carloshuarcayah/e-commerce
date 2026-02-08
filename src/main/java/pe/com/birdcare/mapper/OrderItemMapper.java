package pe.com.birdcare.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.birdcare.dto.OrderItemRequestDTO;
import pe.com.birdcare.dto.OrderItemResponseDTO;
import pe.com.birdcare.entity.OrderItem;

import java.math.BigDecimal;

@Component
public class OrderItemMapper {
    public OrderItemResponseDTO toResponse(OrderItem req){
        BigDecimal subtotal = req.getPrice().multiply(BigDecimal.valueOf(req.getQuantity()));
        return OrderItemResponseDTO.builder()
                .productId(req.getProduct().getId())
                .productName(req.getProduct().getName())
                .price(req.getPrice())
                .quantity(req.getQuantity())
                .subtotal(subtotal)
                .build();
    }
}
