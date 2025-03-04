package org.dawanow.dawanowapi.models;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pharmacists")
public class Pharmacist {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(nullable = false)
    private String pharmacyName;

    @Column
    private String workPermit;

    @Column(nullable = false, columnDefinition = "POINT SRID 4326")
    private Point locationCoordinates;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus status = VerificationStatus.PENDING;

    @Column
    private Long ownerId;
}
