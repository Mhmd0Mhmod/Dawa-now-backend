package org.dawanow.dawanowapi.repositories;

import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Query(value = "SELECT * FROM users WHERE user_role = :role", nativeQuery = true)
    List<User> findByUserRole(String role);

    List<User> findByUserRoleAndOwnerId(UserRole role, int ownerId);






}
