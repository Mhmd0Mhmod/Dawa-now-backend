package org.dawanow.dawanowapi.services.impl;

import org.dawanow.dawanowapi.dto.NearestPharmacyDTO;
import org.dawanow.dawanowapi.dto.UserRegisterResponseDTO;
import org.dawanow.dawanowapi.models.Customer;
import org.dawanow.dawanowapi.models.Pharmacist;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.repositories.CustomerRepository;
import org.dawanow.dawanowapi.repositories.RequestRepository;
import org.dawanow.dawanowapi.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    CustomerRepository customerRepository;


    @Override
    public List<NearestPharmacyDTO> getNearestPharmacies(int requestId, float radius) {
        List<Object[]> results = requestRepository.getNearestPharmacies(requestId, radius);
        return results.stream().map(row -> new NearestPharmacyDTO(
                (String) row[0],               // pharmacyName
                ((Number) row[1]).doubleValue() // distanceMeters
        )).toList();
    }

    @Override
    public void registerCustomer(User user) {
        Customer customer = new Customer();
        customer.setUser(user);
        customerRepository.save(customer);
    }

}
