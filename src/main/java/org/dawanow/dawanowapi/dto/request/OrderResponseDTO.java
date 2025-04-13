package org.dawanow.dawanowapi.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dawanow.dawanowapi.models.OrderStatus;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long orderId;
    private Long requestId;
    private double totalAmount;
    private boolean isDelivery;
    private Long deliveryId;
    private Instant createdAt;
    private OrderStatus orderStatus;

}
