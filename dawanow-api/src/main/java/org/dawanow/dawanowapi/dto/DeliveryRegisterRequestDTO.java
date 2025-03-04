package org.dawanow.dawanowapi.dto;


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
