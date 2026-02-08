package pe.com.birdcare.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.birdcare.dto.OrderItemResponseDTO;
import pe.com.birdcare.dto.OrderRequestDTO;
import pe.com.birdcare.dto.OrderResponseDTO;
import pe.com.birdcare.entity.Order;
import pe.com.birdcare.entity.OrderItem;
import pe.com.birdcare.entity.Product;
import pe.com.birdcare.entity.User;
import pe.com.birdcare.enums.OrderStatus;
import pe.com.birdcare.mapper.OrderMapper;
import pe.com.birdcare.repository.OrderRepository;
import pe.com.birdcare.repository.ProductRepository;
import pe.com.birdcare.repository.UserRepository;
import pe.com.birdcare.service.IOrderService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;

    @Override
    public OrderResponseDTO findById(Long id) {
        return mapper.toResponse(getOrderOrThrow(id));
    }

    @Override
    public Page<OrderResponseDTO> findByUserId(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId,pageable).map(mapper::toResponse);
    }

    @Override
    public OrderResponseDTO create(OrderRequestDTO req) {
        User existingUSer = getUserOrThrow(req.userId());

        Order newOrder = mapper.toEntity(req);
        newOrder.setUser(existingUSer);

        //LOGIC FOR
        List<OrderItem> orderItems = req.items().stream().map(
                itemDTO->{
                    Product product = getProductOrThrow(itemDTO.productId());

                    if (product.getStock() < itemDTO.quantity()) {
                        throw new RuntimeException("Not enough stock for: " + product.getName());
                    }

                    product.setStock(product.getStock() - itemDTO.quantity());

                    return OrderItem.builder()
                            .order(newOrder)
                            .product(product)
                            .quantity(itemDTO.quantity())
                            .price(product.getPrice()).build();
                }).toList();

        newOrder.setItems(orderItems);

        BigDecimal total = orderItems.stream().map(item->getSubtotal(item.getPrice(), item.getQuantity())).reduce(BigDecimal.ZERO,BigDecimal::add);

        newOrder.setTotal(total);

        return mapper.toResponse(orderRepository.save(newOrder));
    }

    @Override
    public OrderResponseDTO update(Long orderId, OrderStatus orderStatus) {
        Order order = getOrderOrThrow(orderId);

        if (order.getStatus() == OrderStatus.DELIVERED && orderStatus == OrderStatus.CANCELLED) {
            throw new RuntimeException("Cannot cancel an order that has already been delivered.");
        }

        order.setStatus(orderStatus);

        if (orderStatus == OrderStatus.CANCELLED) {
            order.getItems().forEach(item -> {
                int currentStock = item.getProduct().getStock();
                item.getProduct().setStock(currentStock + item.getQuantity());
            });
        }

        return mapper.toResponse(orderRepository.save(order));
    }

    private Product getProductOrThrow(Long id){
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found with ID: "+id));
    }

    private User getUserOrThrow(Long id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found with ID: "+id));
    }

    private Order getOrderOrThrow(Long id){
        return orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found with id: "+id));
    }

    private BigDecimal getSubtotal(BigDecimal price, Integer quantity){
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    private OrderItemResponseDTO toOrderItemResponseDTO(OrderItem item){

        BigDecimal subtotal = getSubtotal(item.getPrice(), item.getQuantity());

        return OrderItemResponseDTO.builder()
                .productId(item.getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .subtotal(subtotal).build();
    }

    private List<OrderItemResponseDTO> toListOrderItemResponseDTO(List<OrderItem> items){
        return items.stream().map(this::toOrderItemResponseDTO).toList();
    }

    private OrderResponseDTO toOrderResponseDTO(Order order){
        return OrderResponseDTO.builder().id(order.getId())
                .orderDate(order.getOrderDate())
                .total(order.getTotal())
                .status(order.getStatus().name())
                .shippingAddress(order.getShippingAddress())
                .items(toListOrderItemResponseDTO(order.getItems())).build();
    }
}
