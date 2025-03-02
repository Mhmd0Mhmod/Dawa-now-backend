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
    @Query(value = """
    SELECT pr.id AS provider_id, pr.username,
           ST_Distance_Sphere(ph.location_coordinates, pr.location_coordinates) AS distance_meters
    FROM pharmacists ph
    JOIN providers pr
    ON ST_Distance_Sphere(ph.location_coordinates, pr.location_coordinates) <= :radius
    WHERE ph.id = :pharmacyId
    ORDER BY distance_meters ASC
    """, nativeQuery = true)
    List<User> getNearestProviders(@Param("pharmacyId") int pharmacyId, @Param("radius") float radius);


    @Query(value = """
    SELECT ph.id AS pharmacy_id, ph.username,
           ST_Distance_Sphere(req.location_coordinates, ph.location_coordinates) AS distance_meters
    FROM requests req
    JOIN pharmacists ph
    ON ST_Distance_Sphere(req.location_coordinates, ph.location_coordinates) <= :radius
    WHERE req.id = :requestId
    ORDER BY distance_meters ASC
    """, nativeQuery = true)
    List<User> getNearestPharmacies(@Param("requestId") int requestId, @Param("radius") float radius);





}
