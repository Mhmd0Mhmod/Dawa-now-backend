package org.dawanow.dawanowapi.services.impl;

import org.dawanow.dawanowapi.dto.NearestPharmacyDTO;
import org.dawanow.dawanowapi.models.Pharmacist;
import org.dawanow.dawanowapi.repositories.RequestRepository;
import org.dawanow.dawanowapi.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    RequestRepository requestRepository;


    @Override
    public List<NearestPharmacyDTO> getNearestPharmacies(int requestId, float radius) {
        List<Object[]> results = requestRepository.getNearestPharmacies(requestId, radius);
        return results.stream().map(row -> new NearestPharmacyDTO(
                (String) row[0],               // pharmacyName
                ((Number) row[1]).doubleValue() // distanceMeters
        )).toList();
    }

}
