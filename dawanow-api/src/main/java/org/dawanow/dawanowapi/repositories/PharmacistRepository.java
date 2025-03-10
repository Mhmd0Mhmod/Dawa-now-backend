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
    SELECT pr.username,
           ST_Distance_Sphere(ph.location_coordinates, pr.location_coordinates) AS distance_meters
    FROM pharmacists ph
    JOIN providers pr
    ON ST_Distance_Sphere(ph.location_coordinates, pr.location_coordinates) <= :radius
    WHERE ph.id = :pharmacyId
    ORDER BY distance_meters ASC
    """, nativeQuery = true)
    List<Object[]> getNearestProviders(@Param("pharmacyId") int pharmacyId, @Param("radius") double radius);



}
