package org.dawanow.dawanowapi.services;

import org.dawanow.dawanowapi.dto.DeliveryRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.DeliveryRegisterResponseDTO;
import org.dawanow.dawanowapi.models.User;

public interface DeliveryPersonService {

    public DeliveryRegisterResponseDTO registerDeliveryPerson(User user, DeliveryRegisterRequestDTO deliveryRegisterRequestDTO);
}
