package org.dawanow.dawanowapi.services.impl;

import org.dawanow.dawanowapi.dto.provider.ProviderRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.provider.ProviderRegisterResponseDTO;
import org.dawanow.dawanowapi.models.Provider;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.VerificationStatus;
import org.dawanow.dawanowapi.repositories.ProviderRepository;
import org.dawanow.dawanowapi.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService {


    @Autowired
    ProviderRepository providerRepository;
    public ProviderRegisterResponseDTO registerProvider(User user, ProviderRegisterRequestDTO requestDTO) {
        Provider provider = new Provider();
        provider.setUser(user);
        provider.setProviderName(requestDTO.getProviderName());
        provider.setWorkPermit(requestDTO.getWorkPermit());
        provider.setLocationCoordinates(
                new LocationServiceImpl().createPoint(requestDTO.getLatitude(), requestDTO.getLongitude()));
        provider.setStatus(VerificationStatus.PENDING);
        Provider savedProvider = providerRepository.save(provider);

        return convertToDTO(savedProvider);
    }

    private ProviderRegisterResponseDTO convertToDTO(Provider provider){
        return new ProviderRegisterResponseDTO(
                provider.getProviderName(),
                provider.getWorkPermit(),
                provider.getLocationCoordinates().getX(),
                provider.getLocationCoordinates().getY(),
                provider.getStatus().name()
        );
    }
}
