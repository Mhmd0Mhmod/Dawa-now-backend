package org.dawanow.dawanowapi.services.impl;

import org.dawanow.dawanowapi.dto.pharmacy.PharmacyRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.user.UserRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.user.UserRegisterResponseDTO;
import org.dawanow.dawanowapi.models.UserRole;
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
        return userService.registerUser(
                UserRegisterRequestDTO.builder()
                        .username(pharmacyRegisterRequestDTO.getUsername())
                        .phoneNumber(pharmacyRegisterRequestDTO.getPhoneNumber())
                        .password(pharmacyRegisterRequestDTO.getPassword())
                        .userRole(  UserRole.Pharmacist)
                        .ownerId(adminId)
                        .pharmacyDetails(pharmacyRegisterRequestDTO)
                        .build(),
                UserRole.Pharmacist_Admin
        );

    }

}
