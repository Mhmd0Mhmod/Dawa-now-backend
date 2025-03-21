package org.dawanow.dawanowapi.services.pharmacist;

import org.dawanow.dawanowapi.dto.NearestDeliveryDTO;
import org.dawanow.dawanowapi.dto.NearestProviderDTO;
import org.dawanow.dawanowapi.dto.pharmacy.PharmacyRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.pharmacy.PharmacyRegisterResponseDTO;
import org.dawanow.dawanowapi.models.Pharmacist;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.VerificationStatus;
import org.dawanow.dawanowapi.repositories.PharmacistRepository;
import org.dawanow.dawanowapi.services.location.LocationServiceImpl;
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
    public List<NearestProviderDTO> getNearestProviders(Long pharmacyId, double radius) {
        List<Object[]> results = pharmacistRepository.getNearestProviders(pharmacyId, radius);
        return results.stream().map(row -> new NearestProviderDTO(
                (String) row[0],
                ((Number) row[1]).doubleValue()
        )).toList();
    }

    @Override
    public List<NearestDeliveryDTO> getNearestDelivery(Long pharmacyId, double radius) {
        List<Object[]> results = pharmacistRepository.getNearestDeliveries(pharmacyId,radius);
        return results.stream().map(row -> new NearestDeliveryDTO(
                (String) row[0],
                (String) row[1],
                ((Number) row[2]).doubleValue()
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
