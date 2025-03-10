package org.dawanow.dawanowapi.services;


import org.dawanow.dawanowapi.dto.provider.ProviderRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.provider.ProviderRegisterResponseDTO;
import org.dawanow.dawanowapi.models.User;

public interface ProviderService {

    ProviderRegisterResponseDTO registerProvider(User user, ProviderRegisterRequestDTO requestDTO);

}
