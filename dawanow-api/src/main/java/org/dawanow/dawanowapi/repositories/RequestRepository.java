package org.dawanow.dawanowapi.repositories;

import org.dawanow.dawanowapi.models.Customer;
import org.dawanow.dawanowapi.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(value = """
    SELECT ph.id AS pharmacy_id, ph.username,
           ST_Distance_Sphere(req.location_coordinates, ph.location_coordinates) AS distance_meters
    FROM requests req
    JOIN pharmacists ph
    ON ST_Distance_Sphere(req.location_coordinates, ph.location_coordinates) <= :radius
    WHERE req.id = :requestId
    ORDER BY distance_meters ASC
    """, nativeQuery = true)
    List<Customer> getNearestPharmacies(@Param("requestId") int requestId, @Param("radius") float radius);
}
