package org.dawanow.dawanowapi.dto.delivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRegisterResponseDTO {
    private double longitude;
    private double latitude;
}
