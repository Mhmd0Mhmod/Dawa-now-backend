package org.dawanow.dawanowapi.controllers;

import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.dawanow.dawanowapi.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provider-admins")
public class ProviderAdminController {

    private final UserService userService;

    public ProviderAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllProviderAdmins() {
        return ResponseEntity.ok(userService.getUsersByRole(UserRole.Provider_Admin));
    }

    @GetMapping("/{adminId}/providers")
    public ResponseEntity<List<User>> getProviders(@PathVariable int adminId) {
        return ResponseEntity.ok(userService.getUsersByRoleAndOwner(UserRole.Provider, adminId));
    }
}
