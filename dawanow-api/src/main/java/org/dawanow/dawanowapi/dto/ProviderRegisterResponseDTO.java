package org.dawanow.dawanowapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderRegisterResponseDTO {
    private String providerName;
    private String workPermit;
    private double longitude;
    private double latitude;
    private String status;
}
