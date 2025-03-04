package org.dawanow.dawanowapi.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "delivery_persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPerson {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column
    private Long ownerId;
}
