package org.dawanow.dawanowapi.services;

import org.dawanow.dawanowapi.dto.pharmacy.PharmacyRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.user.UserRegisterResponseDTO;

public interface PharmacistAdminService {

    UserRegisterResponseDTO registerPharmacy(long adminId, PharmacyRegisterRequestDTO pharmacyRegisterRequestDTO);
}
