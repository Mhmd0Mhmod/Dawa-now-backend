package org.dawanow.dawanowapi.controllers;

import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.dawanow.dawanowapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery-admins")
public class DeliveryAdminController {
    private final UserService userService;

    @Autowired
    public DeliveryAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllDeliveryAdmins() {
        return ResponseEntity.ok(userService.getUsersByRole(UserRole.Delivery_Admin));
    }

    @GetMapping("/{adminId}/delivery-persons")
    public ResponseEntity<List<User>> getDeliveryPersons(@PathVariable int adminId) {
        return ResponseEntity.ok(userService.getUsersByRoleAndOwner(UserRole.Delivery, adminId));
    }

}
