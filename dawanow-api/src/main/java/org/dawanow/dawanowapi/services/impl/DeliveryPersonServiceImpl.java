package org.dawanow.dawanowapi.services.impl;

import org.dawanow.dawanowapi.dto.delivery.DeliveryRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.delivery.DeliveryRegisterResponseDTO;
import org.dawanow.dawanowapi.models.DeliveryPerson;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.repositories.DeliveryPersonRepository;
import org.dawanow.dawanowapi.services.DeliveryPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPersonServiceImpl implements DeliveryPersonService {

    @Autowired
    DeliveryPersonRepository deliveryPersonRepository;
    @Autowired
    LocationServiceImpl locationService;
    public DeliveryRegisterResponseDTO registerDeliveryPerson(User user, DeliveryRegisterRequestDTO deliveryRegisterRequestDTO) {
        DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setUser(user);
        deliveryPerson.setIsAvailable(true);
        deliveryPerson.setLocationCoordinates(locationService.createPoint(deliveryRegisterRequestDTO.getLongitude(),deliveryRegisterRequestDTO.getLatitude()));
        deliveryPersonRepository.save(deliveryPerson);
        return new DeliveryRegisterResponseDTO(
                deliveryRegisterRequestDTO.getLongitude(),
                deliveryRegisterRequestDTO.getLatitude(),
                true
        );
    }

}
