package org.dawanow.dawanowapi.controllers;

import org.dawanow.dawanowapi.dto.NearestProviderDTO;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.dawanow.dawanowapi.services.PharmacistService;
import org.dawanow.dawanowapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/pharmacists")
public class PharmacistController {

    private final PharmacistService pharmacistService;
    private final UserService userService;

    public PharmacistController(PharmacistService pharmacistService, UserService userService) {
        this.pharmacistService=pharmacistService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllPharmacists() {
        return ResponseEntity.ok(userService.getUsersByRole(UserRole.Pharmacist));
    }

    @GetMapping("/nearest-providers")
    public ResponseEntity<List<NearestProviderDTO>> getNearestPharmacies(
            @RequestParam int pharmacyId,
            @RequestParam float radius) {
        return ResponseEntity.ok(pharmacistService.getNearestProviders(pharmacyId, radius));
    }

}
