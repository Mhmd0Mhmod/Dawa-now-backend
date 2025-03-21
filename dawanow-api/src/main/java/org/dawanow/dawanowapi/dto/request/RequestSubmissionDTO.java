package org.dawanow.dawanowapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dawanow.dawanowapi.models.RequestStatus;
import org.dawanow.dawanowapi.models.RequestType;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestSubmissionDTO {
    private Long senderId;
    private double longitude;
    private double latitude;
    private String address;
    private List<MedicinePriceDTO> requestedData;
    private double desiredDistance;
    private RequestStatus requestStatus = RequestStatus.Pending;
    private Instant createdAt = Instant.now();
    private RequestType requestType;
}
/*



*/