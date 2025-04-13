package org.dawanow.dawanowapi.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dawanow.dawanowapi.dto.delivery.DeliveryRegisterResponseDTO;
import org.dawanow.dawanowapi.dto.pharmacy.PharmacyRegisterResponseDTO;
import org.dawanow.dawanowapi.dto.provider.ProviderRegisterResponseDTO;
import org.dawanow.dawanowapi.models.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterResponseDTO {
    private Long userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private UserRole userRole;
    private Long ownerId;
    private PharmacyRegisterResponseDTO pharmacyDetails;
    private ProviderRegisterResponseDTO providerDetails;
    private DeliveryRegisterResponseDTO deliveryDetails;

}
