package org.dawanow.dawanowapi.services.customer;


import org.dawanow.dawanowapi.dto.NearestPharmacyDTO;
import org.dawanow.dawanowapi.dto.request.MedicinePriceDTO;
import org.dawanow.dawanowapi.dto.request.RequestResponseDTO;
import org.dawanow.dawanowapi.models.Request;
import org.dawanow.dawanowapi.models.User;

import java.util.List;

public interface CustomerService {

    List<NearestPharmacyDTO> getNearestPharmacies(long requestId, double radius );
    void registerCustomer(User user);
    RequestResponseDTO createRequestToPharmacies(long senderId, double latitude,
                                      double longitude, String address,
                                     List<MedicinePriceDTO> requestedData,
                                      double desiredDistance);
    void createOrder(Long requestId, Long pharmacistId, boolean isDelivery);
}
