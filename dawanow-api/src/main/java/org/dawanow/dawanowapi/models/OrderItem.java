package org.dawanow.dawanowapi.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "medicine_id", nullable = false)
    private Integer medicineId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "is_tape")
    private Boolean isTape;
}

