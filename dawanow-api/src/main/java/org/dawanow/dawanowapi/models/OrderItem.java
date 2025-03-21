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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "medicine_id", nullable = false)
    private Long medicineId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "is_tape")
    private Boolean isTape;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + order +
                ", medicineId=" + medicineId +
                ", quantity=" + quantity +
                ", isTape=" + isTape +
                '}';
    }
}

