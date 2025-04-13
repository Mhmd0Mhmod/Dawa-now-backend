package org.dawanow.dawanowapi.services.customer;


import org.dawanow.dawanowapi.dto.NearestPharmacyDTO;
import org.dawanow.dawanowapi.dto.request.MedicinePriceDTO;
import org.dawanow.dawanowapi.dto.request.OrderResponseDTO;
import org.dawanow.dawanowapi.dto.request.PharmacyResponseDTO;
import org.dawanow.dawanowapi.dto.request.RequestResponseDTO;
import org.dawanow.dawanowapi.models.Request;
import org.dawanow.dawanowapi.models.User;

import java.util.List;

public interface CustomerService {

    List<NearestPharmacyDTO> getNearestPharmacies(Long requestId, double radius );
    void registerCustomer(User user);
    RequestResponseDTO createRequestToPharmacies(Long senderId, double latitude,
                                      double longitude, String address,
                                     List<MedicinePriceDTO> requestedData,
                                      double desiredDistance);
    OrderResponseDTO createOrder(Long requestId, List<PharmacyResponseDTO> pharmacyResponse,
                                 Long pharmacistId, boolean isDelivery);
}
