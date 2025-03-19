package org.dawanow.dawanowapi.controllers;

import org.dawanow.dawanowapi.dto.pharmacy.PharmacyRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.user.UserRegisterResponseDTO;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.dawanow.dawanowapi.repositories.PharmacistRepository;
import org.dawanow.dawanowapi.repositories.UserRepository;
import org.dawanow.dawanowapi.services.pharmacistadmin.PharmacistAdminService;
import org.dawanow.dawanowapi.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacist-admins")
public class PharmacistAdminController {


    @Autowired
    private final UserService userService;
    @Autowired
    private final PharmacistAdminService pharmacistAdminService;


    public PharmacistAdminController(UserService userService, UserRepository userRepository, PharmacistRepository pharmacistRepository, PharmacistAdminService pharmacistAdminService) {
        this.userService = userService;
        this.pharmacistAdminService = pharmacistAdminService;

    }

    @GetMapping
    public ResponseEntity<List<User>> getAllPharmacistAdmins() {
        return ResponseEntity.ok(userService.getUsersByRole(UserRole.Pharmacist_Admin));
    }

    @GetMapping("/{adminId}/pharmacists")
    public ResponseEntity<List<User>> getPharmacists(@PathVariable int adminId) {
        return ResponseEntity.ok(userService.getUsersByRoleAndOwner(UserRole.Pharmacist, adminId));
    }

    @PostMapping("/{adminId}/register")
    public UserRegisterResponseDTO registerPharmacyBranch(@PathVariable int adminId,
                                                          @RequestBody PharmacyRegisterRequestDTO pharmacyRegisterRequestDTO) {

        return pharmacistAdminService.registerPharmacy(adminId,pharmacyRegisterRequestDTO);
    }


    
}
