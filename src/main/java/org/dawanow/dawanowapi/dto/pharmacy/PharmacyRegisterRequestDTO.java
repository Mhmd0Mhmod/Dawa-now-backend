package org.dawanow.dawanowapi.dto.pharmacy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PharmacyRegisterRequestDTO {
    String username;
    String password;
    String phoneNumber;
    private String pharmacyName;
    private String workPermit;
    private double longitude;
    private double latitude;
}
