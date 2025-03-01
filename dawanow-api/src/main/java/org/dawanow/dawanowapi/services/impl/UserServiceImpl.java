package org.dawanow.dawanowapi.services.impl;
import org.dawanow.dawanowapi.dto.RegisterRequestDTO;
import org.dawanow.dawanowapi.dto.RegisterResponseDTO;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.dawanow.dawanowapi.repositories.UserRepository;
import org.dawanow.dawanowapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public RegisterResponseDTO registerUser(RegisterRequestDTO request, UserRole requesterRole) {
        // Validate the requester's role
        if (request.getUserRole() == UserRole.Admin && requesterRole != UserRole.Admin) {
            throw new RuntimeException("Only Admins can create Admin users");
        }
        // Check if the username or email already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
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

        // Create the response
        RegisterResponseDTO response = new RegisterResponseDTO();
        response.setUserId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setPhoneNumber(savedUser.getPhoneNumber());
        response.setUserRole(savedUser.getUserRole());
        response.setOwnerId(savedUser.getOwnerId());
        return response;
    }

}
