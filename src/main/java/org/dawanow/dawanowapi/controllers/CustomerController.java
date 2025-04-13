package org.dawanow.dawanowapi.controllers;


import org.dawanow.dawanowapi.dto.NearestPharmacyDTO;
import org.dawanow.dawanowapi.dto.request.OrderResponseDTO;
import org.dawanow.dawanowapi.dto.request.OrderSubmissionDTO;
import org.dawanow.dawanowapi.dto.request.RequestResponseDTO;
import org.dawanow.dawanowapi.dto.request.RequestSubmissionDTO;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.dawanow.dawanowapi.services.customer.CustomerService;
import org.dawanow.dawanowapi.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam Long requestId,
            @RequestParam double radius) {
        return ResponseEntity.ok(customerService.getNearestPharmacies(requestId, radius));
    }
    @PostMapping("/create-request")
    public ResponseEntity<RequestResponseDTO> createRequest(@RequestBody RequestSubmissionDTO requestDTO) {
        RequestResponseDTO request = customerService.createRequestToPharmacies(
                requestDTO.getSenderId(),
                requestDTO.getLongitude(),
                requestDTO.getLatitude(),
                requestDTO.getAddress(),
                requestDTO.getRequestedData(),
                requestDTO.getDesiredDistance()
        );

        return ResponseEntity.ok(request);
    }

    @PostMapping("/confirm-order")
    public ResponseEntity<OrderResponseDTO> confirmOrder(
            @RequestBody OrderSubmissionDTO confirmRequestDTO
    ){
        OrderResponseDTO orderResponseDTO = customerService.createOrder(confirmRequestDTO.getRequestId(),
                confirmRequestDTO.getPharmacyResponses(),
                confirmRequestDTO.getPharmacyId(),
                confirmRequestDTO.isDelivery());


        return ResponseEntity.ok(orderResponseDTO);

    }


}
