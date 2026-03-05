package com.example.orderservice.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "total")
    private BigDecimal totalPrice;

    @Column(name="quantity")
    private Long ticketCount;

    @CreationTimestamp
    @Column(name = "placed_at", updatable = false, nullable = false)
    private LocalDateTime placedAt;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "event_id")
    private String eventId;


}
