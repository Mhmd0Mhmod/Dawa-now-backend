package org.dawanow.dawanowapi.services;


import org.dawanow.dawanowapi.dto.NearestPharmacyDTO;
import org.dawanow.dawanowapi.dto.PharmacyRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.PharmacyRegisterResponseDTO;
import org.dawanow.dawanowapi.dto.UserRegisterResponseDTO;
import org.dawanow.dawanowapi.models.User;

import java.util.List;

public interface CustomerService {

    List<NearestPharmacyDTO> getNearestPharmacies(int requestId, float radius );
    void registerCustomer(User user);

}
