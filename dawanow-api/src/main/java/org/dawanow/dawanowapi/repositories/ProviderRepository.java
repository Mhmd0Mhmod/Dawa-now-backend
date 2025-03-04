package org.dawanow.dawanowapi.repositories;

import org.dawanow.dawanowapi.models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProviderRepository extends JpaRepository<Provider,Long> {

}
