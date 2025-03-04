package org.dawanow.dawanowapi.services;

import org.dawanow.dawanowapi.dto.UserRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.UserRegisterResponseDTO;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;

import java.util.List;


public interface UserService {

    
    List<User> getAllUsers();
    User getUserById(Integer id);
    List<User> getUsersByRole(UserRole role);
    List<User> getUsersByRoleAndOwner(UserRole role, int ownerId);
    UserRegisterResponseDTO registerUser(UserRegisterRequestDTO request, UserRole userRole);


}

