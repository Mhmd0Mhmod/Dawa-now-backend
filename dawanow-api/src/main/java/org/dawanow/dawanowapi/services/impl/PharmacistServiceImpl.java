package org.dawanow.dawanowapi.services.impl;

import org.dawanow.dawanowapi.dto.NearestProviderDTO;
import org.dawanow.dawanowapi.repositories.PharmacistRepository;
import org.dawanow.dawanowapi.services.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class PharmacistServiceImpl implements PharmacistService {


    PharmacistRepository pharmacistRepository;

    @Autowired
    public PharmacistServiceImpl (PharmacistRepository pharmacistRepository){
        this.pharmacistRepository = pharmacistRepository;
    }

    @Override
    public List<NearestProviderDTO> getNearestProviders(int pharmacyId, float radius) {
        List<Object[]> results = pharmacistRepository.getNearestProviders(pharmacyId, radius);
        return results.stream().map(row -> new NearestProviderDTO(
                (String) row[0],
                ((Number) row[1]).doubleValue()
        )).toList();
    }
}
