package org.dawanow.dawanowapi.models;

import jakarta.persistence.*;
import lombok.*;

import org.locationtech.jts.geom.Point;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_assignment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "pharmacist_id", nullable = false)
    private Integer pharmacistId;

    @Column(name = "delivery_id")
    private Integer deliveryId;

    @Column(name = "assigned_at", nullable = false, updatable = false)
    private LocalDateTime assignedAt = LocalDateTime.now();

    @Column(name = "location_coordinates", nullable = false)
    private Point locationCoordinates;
}

