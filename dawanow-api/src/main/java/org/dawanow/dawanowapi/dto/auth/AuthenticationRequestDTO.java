package org.dawanow.dawanowapi.dto.auth;

import lombok.Data;

@Data

public class AuthenticationRequestDTO {
    private String username;
    private String password;
}
