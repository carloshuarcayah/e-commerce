package pe.com.birdcare.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.birdcare.dto.AdminOrderRequestDTO;
import pe.com.birdcare.dto.OrderResponseDTO;
import pe.com.birdcare.enums.OrderStatus;
import pe.com.birdcare.service.IOrderService;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
    private final IOrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<OrderResponseDTO>> findByUserId(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(orderService.findByUserId(userId, pageable));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody AdminOrderRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(req));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }
}