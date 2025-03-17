package org.dawanow.dawanowapi.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dawanow.dawanowapi.dto.NearestPharmacyDTO;
import org.dawanow.dawanowapi.dto.request.MedicinePriceDTO;
import org.dawanow.dawanowapi.dto.request.PharmacyResponseDTO;
import org.dawanow.dawanowapi.dto.request.RequestResponseDTO;
import org.dawanow.dawanowapi.models.Customer;
import org.dawanow.dawanowapi.models.Request;
import org.dawanow.dawanowapi.models.RequestStatus;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.repositories.CustomerRepository;
import org.dawanow.dawanowapi.repositories.RequestRepository;
import org.dawanow.dawanowapi.repositories.UserRepository;
import org.dawanow.dawanowapi.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    LocationServiceImpl locationService;
    @Autowired
    UserRepository userRepository;
    

    @Override
    public List<NearestPharmacyDTO> getNearestPharmacies(long requestId, double radius) {
        List<Object[]> results = requestRepository.getNearestPharmacies(requestId, radius);
        return results.stream().map(row -> new NearestPharmacyDTO(
                (int) row[0],
                (String) row[1],               // pharmacyName
                ((Number) row[2]).doubleValue() // distanceMeters
        )).toList();
    }

    @Override
    public void registerCustomer(User user) {
        Customer customer = new Customer();
        customer.setUser(user);
        customerRepository.save(customer);
    }

    @Override
    public RequestResponseDTO createRequestToPharmacies(long senderId, double latitude,
                                             double longitude, String address,
                                             List<MedicinePriceDTO> requestedData,
                                             double desiredDistance) {
        Request request = new Request();
        request.setSender(userRepository.getReferenceById(senderId));

        // Converting MedicinePriceDTO to json
        request.setRequestData(convertMedicinePriceDtoToJson(requestedData));

        request.setLocationCoordinates(locationService.createPoint(longitude, latitude));
        request.setAddress(address);
        request.setRequestStatus(RequestStatus.Pending);

        // Saving the request without receiverIds
        Request savedRequest = requestRepository.save(request);

        System.out.println(MedicinePriceDTO.getRequestDataAsObject(request));

        // Get nearest pharmacies and update receiver IDs
        List<NearestPharmacyDTO> nearestPharmacies = getNearestPharmacies(savedRequest.getId(), desiredDistance);
        savedRequest.setReceiverIds(extractReceiverIds(nearestPharmacies).toString());

        // :: Should Implement Sending requests to those receiver Ids


        // Build list of PharmacyResponseDTOs
        List<PharmacyResponseDTO> pharmacyResponses = nearestPharmacies.stream()
                .map(pharmacy -> PharmacyResponseDTO.builder()
                        .medicines(requestedData)
                        .nearestPharmacyDTO(pharmacy)
                        .build())
                .toList();

        System.out.println(pharmacyResponses);
        // :: Check If he wants delivery first, if he wants then search for delivery if you found
        // Then you can create order otherwise the order will not be done.

        return mapToRequestResponseDTO(request,pharmacyResponses);
    }

    private String convertMedicinePriceDtoToJson(List<MedicinePriceDTO> requestedData) {
        try {
            return new ObjectMapper().writeValueAsString(requestedData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert MedicinePriceDTO to JSON", e);
        }
    }
    private List<Integer> extractReceiverIds(List<NearestPharmacyDTO> nearestPharmacyDTO){
        return nearestPharmacyDTO.stream()
                .map(NearestPharmacyDTO::getId)
                .collect(Collectors.toList());
    }

    private RequestResponseDTO mapToRequestResponseDTO(Request request, List<PharmacyResponseDTO> pharmacyResponseDTOS){
        new RequestResponseDTO();
        return RequestResponseDTO.builder()
                .requestId(request.getId())
                .senderId(request.getSender().getId())
                .requestStatus(RequestStatus.Pending)
                .pharmacyResponses(pharmacyResponseDTOS)
                .createdAt(Instant.now())
                .build();
    }

    @Override
    public void createOrder(Long requestId, Long pharmacistId, boolean isDelivery) {
        /*
        1- Check if it's
        2-
        3-
        */
    }

}
