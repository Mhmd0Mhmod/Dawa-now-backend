package org.dawanow.dawanowapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dawanow.dawanowapi.models.RequestStatus;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestResponseDTO {
    private Long requestId;
    private Long senderId;
    private List<PharmacyResponseDTO> pharmacyResponses;
    private RequestStatus requestStatus;
    private Instant createdAt = Instant.now();
}

/*
{
    "requestId": 123,
    "senderId": 10,
    "pharmacyResponses": [
        {
            "receiverId": 1,
            "pharmacyName": "Anas",
            "distance" : 80,
            "medicines": [
                {
                    "medicine": "Paracetamol",
                    "quantity": 2,
                    "price": 10.0
                },
                {
                    "medicine": "Aspirin",
                    "quantity": 3,
                    "price": 50.0
                }
            ]
        },
        {
            "receiverId": 2,
            "medicines": [
                {
                    "medicine": "Paracetamol",
                    "quantity": 2,
                    "price": 12.0
                },
                {
                    "medicine": "Aspirin",
                    "quantity": 3,
                    "price": 55.0
                }
            ]
        }
    ],
    "requestStatus": "Pending",
    "createdAt": "2024-03-05T12:00:00Z"
}




 */
