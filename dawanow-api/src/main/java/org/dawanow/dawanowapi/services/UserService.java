package org.dawanow.dawanowapi.services;

import org.dawanow.dawanowapi.dto.RegisterRequestDTO;
import org.dawanow.dawanowapi.dto.RegisterResponseDTO;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface UserService {

    
    List<User> getAllUsers();
    User getUserById(Integer id);
    List<User> getUsersByRole(UserRole role);
    List<User> getUsersByRoleAndOwner(UserRole role, int ownerId);
    RegisterResponseDTO registerUser(RegisterRequestDTO request, UserRole userRole);

}

