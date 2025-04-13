package org.dawanow.dawanowapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

import lombok.ToString;
import org.dawanow.dawanowapi.dto.request.MedicinePriceDTO;
import org.locationtech.jts.geom.Point;


@Entity
@Table(name = "requests")
@Getter
@Setter
@ToString
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(name = "location_coordinates", columnDefinition = "POINT SRID 4326", nullable = false)
    private Point locationCoordinates;

    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "receiver_ids", columnDefinition = "JSON")
    private String receiverIds;

    @Column(name = "request", columnDefinition = "JSON", nullable = false)
    private String requestData;

    @Column(name = "response", columnDefinition = "JSON")
    private String response;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus requestStatus = RequestStatus.Pending;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestType requestType = RequestType.To_Pharmacy ;



}
