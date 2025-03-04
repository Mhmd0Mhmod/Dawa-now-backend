package org.dawanow.dawanowapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dawanow.dawanowapi.models.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public UserRegisterRequestDTO(String username, String email, String phoneNumber, String password, UserRole userRole, Long ownerId, PharmacyRegisterRequestDTO pharmacyDetails) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRole = userRole;
        this.ownerId = ownerId;
        this.pharmacyDetails = pharmacyDetails;
    }

    public UserRegisterRequestDTO(String username, String email, String phoneNumber, String password, UserRole userRole, Long ownerId) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRole = userRole;
        this.ownerId = ownerId;
    }

    public UserRegisterRequestDTO(String username, String email, String phoneNumber, String password, UserRole userRole, Long ownerId, DeliveryRegisterRequestDTO deliveryPersonDetails) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRole = userRole;
        this.ownerId = ownerId;
        this.deliveryPersonDetails = deliveryPersonDetails;
    }

    public UserRegisterRequestDTO(String username, String email, String phoneNumber, String password, UserRole userRole, Long ownerId, ProviderRegisterRequestDTO providerDetails) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRole = userRole;
        this.ownerId = ownerId;
        this.providerDetails = providerDetails;
    }
}
