package org.dawanow.dawanowapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;
import org.locationtech.jts.geom.Point;


@Entity
@Table(name = "requests")
@Getter
@Setter
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(name = "location_coordinates", columnDefinition = "POINT SRID 4326", nullable = false)
    private Point locationCoordinates;

    @Column(name = "receiver_ids", columnDefinition = "JSON", nullable = false)
    private String receiverIds;

    @Column(name = "request", columnDefinition = "JSON", nullable = false)
    private String requestData;

    @Column(name = "response", columnDefinition = "JSON")
    private String response;

    @Column(name = "request_status", nullable = false, length = 50)
    private String requestStatus;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "request_type", nullable = false, length = 50)
    private String requestType;
}
