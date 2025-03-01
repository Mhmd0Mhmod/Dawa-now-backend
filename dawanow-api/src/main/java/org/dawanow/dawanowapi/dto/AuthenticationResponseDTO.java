package org.dawanow.dawanowapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dawanow.dawanowapi.models.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO {
    private String token;
    private Long userId;
    private String username;
    private UserRole role;
    private Long ownerId;
}
