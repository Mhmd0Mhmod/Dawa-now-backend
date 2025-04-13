package org.dawanow.dawanowapi.services.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dawanow.dawanowapi.dto.NearestPharmacyDTO;
import org.dawanow.dawanowapi.dto.request.*;
import org.dawanow.dawanowapi.models.*;
import org.dawanow.dawanowapi.repositories.*;
import org.dawanow.dawanowapi.services.location.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    PharmacistRepository pharmacistRepository;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    LocationServiceImpl locationService;
    @Autowired
    UserRepository userRepository;
    

    @Override
    public List<NearestPharmacyDTO> getNearestPharmacies(Long requestId, double radius) {
        List<Object[]> results = requestRepository.getNearestPharmacies(requestId, radius);
        return results.stream().map(row -> new NearestPharmacyDTO(
                ((Number) row[0]).longValue(),
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
    public RequestResponseDTO createRequestToPharmacies(Long senderId, double latitude,
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
        // :: Should Implement that the pharmacist adds the alternatives not in the request.


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

        // Simulate that the person chose a pharmacy and delivery or not
        createOrder(savedRequest.getId(),pharmacyResponses,56L,true);
        return mapToRequestResponseDTO(request,pharmacyResponses);
    }



    private String convertMedicinePriceDtoToJson(List<MedicinePriceDTO> requestedData) {
        try {
            return new ObjectMapper().writeValueAsString(requestedData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert MedicinePriceDTO to JSON", e);
        }
    }
    private List<Long> extractReceiverIds(List<NearestPharmacyDTO> nearestPharmacyDTO){
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
    public OrderResponseDTO createOrder(Long requestId, List<PharmacyResponseDTO> pharmacyResponseDTO,
                                        Long pharmacistId, boolean isDelivery) {
        // Get total amount
        double totalAmount = getTotalAmount(pharmacyResponseDTO, pharmacistId);

        // Create the order with Pending status
        Order order = new Order();
        order.setRequestId(requestId);
        order.setTotalAmount(totalAmount);
        order.setOrderAssignment(null);  // No assignment yet
        order.setIsDelivery(isDelivery);
        order.setOrderStatus(OrderStatus.PENDING_DELIVERY);

        Order savedOrder = orderRepository.save(order);

        // Get Order Items
        List<OrderItem> medicineList = getMedicines(pharmacyResponseDTO, pharmacistId, savedOrder);
        orderItemRepository.saveAll(medicineList);

        // If delivery is required, find a delivery person
        if (isDelivery) {
            List<Object[]> nearestDeliveries = pharmacistRepository.getNearestDeliveries(pharmacistId, 5000);

            if (nearestDeliveries.isEmpty()) {
                throw new RuntimeException("No delivery person available at the moment.");
            }

            // Send request to delivery person
            for (Object[] deliveryPerson : nearestDeliveries) {
//                sendDeliveryRequest(order, deliveryPerson);
            }

            //::  Wait for a response (WebSocket or event-driven approach recommended)
        }

        return new OrderResponseDTO(
                savedOrder.getId(),
                requestId,
                totalAmount,
                isDelivery,
                // assignedDeliveryId,
                null,
                Instant.now(),
                savedOrder.getOrderStatus()
        );
    }


    private List<OrderItem> getMedicines(List<PharmacyResponseDTO> pharmacyResponseDTO, Long pharmacistId, Order order) {
        return pharmacyResponseDTO.stream()
                .filter(response -> response.getNearestPharmacyDTO().getId().equals(pharmacistId))
                .flatMap(response -> response.getMedicines().stream())
                .flatMap(medicine -> {
                    List<OrderItem> orderItems = medicine.getAlternatives().stream()
                            .filter(AlternativeMedicineDTO::isChosen) // Get all chosen alternatives
                            .map(alternative -> new OrderItem(null, order, alternative.getId(), alternative.getQuantity(), medicine.isTape()))
                            .collect(Collectors.toList());

                    if (orderItems.isEmpty()) {
                        orderItems.add(new OrderItem(null, order, medicine.getId(), medicine.getQuantity(), medicine.isTape()));
                    }

                    return orderItems.stream();
                })
                .collect(Collectors.toList());
    }


    private double getTotalAmount(List<PharmacyResponseDTO> pharmacyResponses, Long pharmacistId) {
        return pharmacyResponses.stream()
                .filter(response -> response.getNearestPharmacyDTO().getId().equals(pharmacistId)) // Find matching pharmacy
                .flatMap(response -> response.getMedicines().stream()) // Flatten medicines list
                .map(medicine -> {
                    return medicine.getAlternatives().stream()
                            .filter(AlternativeMedicineDTO::isChosen) // Find chosen alternative
                            .findFirst()
                            .map(alt -> BigDecimal.valueOf(alt.getPrice()).multiply(BigDecimal.valueOf(alt.getQuantity()))) // Use chosen alternative
                            .orElse(BigDecimal.valueOf(medicine.getPrice()).multiply(BigDecimal.valueOf(medicine.getQuantity()))); // Default to original medicine
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }

}
