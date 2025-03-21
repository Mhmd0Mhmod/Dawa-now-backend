package org.dawanow.dawanowapi.repositories;

import org.dawanow.dawanowapi.models.Pharmacist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {

    @Query(value = """
    SELECT pr.id,pr.provider_name,
           ST_Distance_Sphere(ph.location_coordinates, pr.location_coordinates) AS distance_meters
    FROM pharmacists ph
    JOIN providers pr
    ON ST_Distance_Sphere(ph.location_coordinates, pr.location_coordinates) <= :radius
    WHERE ph.id = :pharmacyId
    ORDER BY distance_meters ASC
    """, nativeQuery = true)
    List<Object[]> getNearestProviders(@Param("pharmacyId") Long pharmacyId, @Param("radius") double radius);

    @Query(value = """
    SELECT u.username,u.phone_number,
           ST_Distance_Sphere(ph.location_coordinates, d.location_coordinates) AS distance_meters
    FROM pharmacists ph
    JOIN delivery_persons d ON ST_Distance_Sphere(ph.location_coordinates, d.location_coordinates) <= :radius
    JOIN users u ON d.id = u.id
    WHERE ph.id = :pharmacyId
    ORDER BY distance_meters ASC
    """, nativeQuery = true)
    List<Object[]> getNearestDeliveries(@Param("pharmacyId") Long pharmacyId, @Param("radius") double radius);


}
