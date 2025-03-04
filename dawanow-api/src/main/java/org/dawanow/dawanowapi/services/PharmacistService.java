package org.dawanow.dawanowapi.services;

import org.dawanow.dawanowapi.dto.NearestProviderDTO;
import org.dawanow.dawanowapi.dto.PharmacyRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.PharmacyRegisterResponseDTO;
import org.dawanow.dawanowapi.models.User;

import java.util.List;

public interface PharmacistService {

    List<NearestProviderDTO> getNearestProviders(int pharmacyId, float radius);
    PharmacyRegisterResponseDTO registerPharmacist(User user, PharmacyRegisterRequestDTO requestDTO);

}
