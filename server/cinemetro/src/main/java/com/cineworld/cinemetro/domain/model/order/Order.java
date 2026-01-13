package com.cineworld.cinemetro.domain.model.order;

import com.cineworld.cinemetro.domain.model.User;
import com.cineworld.cinemetro.domain.model.product.Product;
import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---- Relations ----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // Placeholder for now

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<Ticket> tickets;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    // ---- Fields ----
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private BigDecimal discountValue; // % or fixed amount
    private BigDecimal totalAmount;

    private LocalDateTime createdAt;

    // ---- Utility ----
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null)
            this.status = OrderStatus.PENDING;
        if (this.discountType == null)
            this.discountType = DiscountType.NONE;
    }
}
