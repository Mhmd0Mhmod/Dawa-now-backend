package org.dawanow.dawanowapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NearestDeliveryDTO {
    private String deliveryUsername;
    private String phoneNumber;
    private Double distanceMeters;
}
