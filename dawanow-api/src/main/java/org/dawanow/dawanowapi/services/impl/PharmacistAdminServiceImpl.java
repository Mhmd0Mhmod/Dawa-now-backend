package org.dawanow.dawanowapi.services.impl;

import org.dawanow.dawanowapi.dto.PharmacyRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.PharmacyRegisterResponseDTO;
import org.dawanow.dawanowapi.dto.UserRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.UserRegisterResponseDTO;
import org.dawanow.dawanowapi.models.Pharmacist;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.dawanow.dawanowapi.models.VerificationStatus;
import org.dawanow.dawanowapi.repositories.UserRepository;
import org.dawanow.dawanowapi.services.PharmacistAdminService;
import org.dawanow.dawanowapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PharmacistAdminServiceImpl implements PharmacistAdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private UserService userService;



    @Override
    public UserRegisterResponseDTO registerPharmacy(long adminId, PharmacyRegisterRequestDTO pharmacyRegisterRequestDTO) {
        User user = new User();
        return userService.registerUser(
                new UserRegisterRequestDTO(
                        pharmacyRegisterRequestDTO.getUsername(),
                        null,
                        pharmacyRegisterRequestDTO.getPhoneNumber(),
                        pharmacyRegisterRequestDTO.getPassword(),
                        UserRole.Pharmacist,
                        adminId,
                        pharmacyRegisterRequestDTO
                ),
                UserRole.Pharmacist_Admin
        );
    }

}
