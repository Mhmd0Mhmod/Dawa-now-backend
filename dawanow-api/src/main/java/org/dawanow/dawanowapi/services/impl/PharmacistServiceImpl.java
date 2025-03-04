package org.dawanow.dawanowapi.services.impl;

import org.dawanow.dawanowapi.dto.NearestProviderDTO;
import org.dawanow.dawanowapi.dto.PharmacyRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.PharmacyRegisterResponseDTO;
import org.dawanow.dawanowapi.models.Pharmacist;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.VerificationStatus;
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

    public PharmacyRegisterResponseDTO registerPharmacist(User user, PharmacyRegisterRequestDTO requestDTO) {
        Pharmacist pharmacist = new Pharmacist();
        pharmacist.setUser(user);
        pharmacist.setPharmacyName(requestDTO.getPharmacyName());
        pharmacist.setWorkPermit(requestDTO.getWorkPermit());
        pharmacist.setLocationCoordinates(
                new LocationServiceImpl().createPoint(requestDTO.getLatitude(), requestDTO.getLongitude()));
        pharmacist.setStatus(VerificationStatus.PENDING);
        Pharmacist savedPharmacist = pharmacistRepository.save(pharmacist);

        return convertToDTO(savedPharmacist);
    }

    private PharmacyRegisterResponseDTO convertToDTO(Pharmacist pharmacist){
        return new PharmacyRegisterResponseDTO(
                pharmacist.getPharmacyName(),
                pharmacist.getWorkPermit(),
                pharmacist.getLocationCoordinates().getX(),
                pharmacist.getLocationCoordinates().getY(),
                pharmacist.getStatus().name()
        );
    }
}
