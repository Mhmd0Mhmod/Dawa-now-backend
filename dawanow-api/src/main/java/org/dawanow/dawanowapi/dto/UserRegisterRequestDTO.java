package org.dawanow.dawanowapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dawanow.dawanowapi.models.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequestDTO {
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private UserRole userRole;
    private Long ownerId;
    private PharmacyRegisterRequestDTO pharmacyDetails = null;
    private DeliveryRegisterRequestDTO deliveryPersonDetails = null;
    private ProviderRegisterRequestDTO providerDetails = null;


}
