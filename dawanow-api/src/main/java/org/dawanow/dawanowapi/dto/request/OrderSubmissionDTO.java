package org.dawanow.dawanowapi.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubmissionDTO {
    private Long requestId;
    private List<PharmacyResponseDTO> pharmacyResponses;
    private Long pharmacyId;
    private boolean isDelivery;
}
