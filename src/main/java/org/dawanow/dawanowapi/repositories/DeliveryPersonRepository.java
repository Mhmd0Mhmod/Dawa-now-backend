package org.dawanow.dawanowapi.repositories;

import org.dawanow.dawanowapi.models.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson,Long> {

}
