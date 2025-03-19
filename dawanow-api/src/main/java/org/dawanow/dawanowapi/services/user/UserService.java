package org.dawanow.dawanowapi.services.user;

import org.dawanow.dawanowapi.dto.user.UserRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.user.UserRegisterResponseDTO;
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

