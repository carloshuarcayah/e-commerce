package pe.com.birdcare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import pe.com.birdcare.enums.OrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //ID FOR THE MAPPER - RESPONSE
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Column(nullable = false)
    private String shippingAddress;

    //IGNORE IN MAPPER
    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime orderDate = LocalDateTime.now();

    //IGNORE IN MAPPER
    @Column(nullable = false, precision = 10,scale = 2)
    @PositiveOrZero
    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderItem> items =new ArrayList<>();

    //IGNORE IN MAPPER
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING; // Set a default!
}
