package org.dawanow.dawanowapi.dto.delivery;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRegisterRequestDTO {
    private double longitude;
    private double latitude;
}
