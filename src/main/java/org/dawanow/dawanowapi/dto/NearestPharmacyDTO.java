package org.dawanow.dawanowapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NearestPharmacyDTO {
    private Long id;
    private String pharmacyName;
    private Double distanceMeters;
}
