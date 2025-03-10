package org.dawanow.dawanowapi.services;

import org.dawanow.dawanowapi.dto.delivery.DeliveryRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.delivery.DeliveryRegisterResponseDTO;
import org.dawanow.dawanowapi.models.User;

public interface DeliveryPersonService {

    public DeliveryRegisterResponseDTO registerDeliveryPerson(User user, DeliveryRegisterRequestDTO deliveryRegisterRequestDTO);
}
