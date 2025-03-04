package org.dawanow.dawanowapi.services.impl;
import org.dawanow.dawanowapi.dto.UserRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.UserRegisterResponseDTO;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.dawanow.dawanowapi.repositories.UserRepository;
import org.dawanow.dawanowapi.services.CustomerService;
import org.dawanow.dawanowapi.services.DeliveryPersonService;
import org.dawanow.dawanowapi.services.PharmacistService;
import org.dawanow.dawanowapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PharmacistService pharmacistService;

    @Autowired
    private DeliveryPersonService deliveryPersonService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Override
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByUserRole(role.name());
    }

    public List<User> getUsersByRoleAndOwner(UserRole role, int ownerId) {
        return userRepository.findByUserRoleAndOwnerId(role, ownerId);
    }
    @Transactional

    public UserRegisterResponseDTO registerUser(UserRegisterRequestDTO request, UserRole requesterRole) {
        // Validate the requester's role
        if (request.getUserRole() == UserRole.Admin && requesterRole != UserRole.Admin) {
            throw new RuntimeException("Only Admins can create Admin users");
        }
        // Check if the username or email already exists
        System.out.println(request);
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (request.getEmail() != null && userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create a new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setHashedPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserRole(request.getUserRole());
        user.setOwnerId(request.getOwnerId());

        // Save the user to the database
        User savedUser = userRepository.save(user);

        UserRegisterResponseDTO response = new UserRegisterResponseDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber(),
                savedUser.getUserRole(),
                savedUser.getOwnerId()
        );

        switch (savedUser.getUserRole()) {
            case Pharmacist:
                if (request.getPharmacyDetails() == null) {
                    throw new RuntimeException("Pharmacy details are required for Pharmacists");
                }
                response.setPharmacyDetails(
                        pharmacistService.registerPharmacist(
                                savedUser, request.getPharmacyDetails()));
                break;

            case Delivery:
                response.setDeliveryDetails(
                        deliveryPersonService.registerDeliveryPerson(
                                savedUser,request.getDeliveryPersonDetails()
                        ));
                break;

            case Provider:
                // Handle Provider role logic
                break;

            case Customer:
                customerService.registerCustomer(user);
                break;

            case Admin:
            case Pharmacist_Admin:
            case Delivery_Admin:
            case Provider_Admin:
                // Handle Admin roles logic (if needed)
                break;

            default:
                throw new RuntimeException("Unsupported user role: " + savedUser.getUserRole());
        }
        // Create the response

        return response;
    }
}
