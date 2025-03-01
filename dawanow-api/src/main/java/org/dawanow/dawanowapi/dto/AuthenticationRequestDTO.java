package org.dawanow.dawanowapi.dto;

import lombok.Data;

@Data

public class AuthenticationRequestDTO {
    private String username;
    private String password;
}
