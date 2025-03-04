package org.dawanow.dawanowapi.services;

import org.dawanow.dawanowapi.dto.PharmacyRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.PharmacyRegisterResponseDTO;
import org.dawanow.dawanowapi.dto.UserRegisterResponseDTO;

public interface PharmacistAdminService {

    UserRegisterResponseDTO registerPharmacy(long adminId, PharmacyRegisterRequestDTO pharmacyRegisterRequestDTO);
}
