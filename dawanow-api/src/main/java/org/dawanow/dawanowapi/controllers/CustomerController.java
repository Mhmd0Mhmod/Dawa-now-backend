package org.dawanow.dawanowapi.controllers;


import org.dawanow.dawanowapi.dto.NearestPharmacyDTO;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.dawanow.dawanowapi.services.CustomerService;
import org.dawanow.dawanowapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    private final UserService userService;

    public CustomerController(CustomerService customerService, UserService userService) {
        this.customerService=customerService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllCustomers() {
        return ResponseEntity.ok(userService.getUsersByRole(UserRole.Customer));
    }

    @GetMapping("/nearest-pharmacies")
    public ResponseEntity<List<NearestPharmacyDTO>> getNearestPharmacies(
            @RequestParam int requestId,
            @RequestParam float radius) {
        return ResponseEntity.ok(customerService.getNearestPharmacies(requestId, radius));
    }

}
