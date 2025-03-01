package org.dawanow.dawanowapi.controllers;

import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.dawanow.dawanowapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacist-admins")
public class PharmacistAdminController {

    private final UserService userService;

    public PharmacistAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllPharmacistAdmins() {
        return ResponseEntity.ok(userService.getUsersByRole(UserRole.Pharmacist_Admin));
    }

    @GetMapping("/{adminId}/pharmacists")
    public ResponseEntity<List<User>> getPharmacists(@PathVariable int adminId) {
        return ResponseEntity.ok(userService.getUsersByRoleAndOwner(UserRole.Pharmacist, adminId));
    }

    
}
