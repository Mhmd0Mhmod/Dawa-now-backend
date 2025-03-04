package org.dawanow.dawanowapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dawanow.dawanowapi.models.UserRole;

@Data
@NoArgsConstructor
public class UserRegisterResponseDTO {
    private Long userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private UserRole userRole;
    private Long ownerId;
    public UserRegisterResponseDTO(Long userId, String username, String email, String phoneNumber, UserRole userRole, Long ownerId) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
        this.ownerId = ownerId;
    }
    private PharmacyRegisterResponseDTO pharmacyDetails;
    private ProviderRegisterResponseDTO providerDetails;
    private DeliveryRegisterResponseDTO deliveryDetails;
}
