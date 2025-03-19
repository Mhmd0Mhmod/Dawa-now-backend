package org.dawanow.dawanowapi.services.user;
import org.dawanow.dawanowapi.dto.delivery.DeliveryRegisterResponseDTO;
import org.dawanow.dawanowapi.dto.pharmacy.PharmacyRegisterResponseDTO;
import org.dawanow.dawanowapi.dto.provider.ProviderRegisterResponseDTO;
import org.dawanow.dawanowapi.dto.user.UserRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.user.UserRegisterResponseDTO;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.dawanow.dawanowapi.repositories.UserRepository;
import org.dawanow.dawanowapi.services.customer.CustomerService;
import org.dawanow.dawanowapi.services.deliveryperson.DeliveryPersonService;
import org.dawanow.dawanowapi.services.pharmacist.PharmacistService;
import org.dawanow.dawanowapi.services.provider.ProviderService;
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
    private ProviderService providerService;


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
        if (request.getUserRole() == UserRole.Admin && requesterRole != UserRole.Admin) {
            throw new RuntimeException("Only Admins can create Admin users");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (request.getEmail() != null && userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setHashedPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserRole(request.getUserRole());
        user.setOwnerId(request.getOwnerId());

        User savedUser = userRepository.save(user);

        // Register additional details based on the user role
        PharmacyRegisterResponseDTO pharmacyDetails = null;
        DeliveryRegisterResponseDTO deliveryDetails = null;
        ProviderRegisterResponseDTO providerDetails = null;

        switch (savedUser.getUserRole()) {
            case Pharmacist:
                if (request.getPharmacyDetails() == null) {
                    throw new RuntimeException("Pharmacy details are required for Pharmacists");
                }
                pharmacyDetails = pharmacistService.registerPharmacist(savedUser, request.getPharmacyDetails());
                break;

            case Delivery:
                deliveryDetails = deliveryPersonService.registerDeliveryPerson(savedUser, request.getDeliveryPersonDetails());
                break;

            case Provider:
                if (request.getProviderDetails() == null) {
                    throw new RuntimeException("Provider details are required for providers");
                }
                providerDetails = providerService.registerProvider(savedUser, request.getProviderDetails());
                break;

            case Customer:
                customerService.registerCustomer(user);
                break;

            default:
                throw new RuntimeException("Unsupported user role: " + savedUser.getUserRole());
        }

        return UserRegisterResponseDTO.builder()
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .userRole(savedUser.getUserRole())
                .ownerId(savedUser.getOwnerId())
                .pharmacyDetails(pharmacyDetails)  // Now assigned directly in builder
                .deliveryDetails(deliveryDetails)
                .providerDetails(providerDetails)
                .build();
    }

}
