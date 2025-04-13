package org.dawanow.dawanowapi.dto.provider;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderRegisterRequestDTO {
    String username;
    String password;
    String phoneNumber;
    private String providerName;
    private String workPermit;
    private double longitude;
    private double latitude;
}
