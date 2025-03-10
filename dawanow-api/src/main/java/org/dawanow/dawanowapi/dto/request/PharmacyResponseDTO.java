package org.dawanow.dawanowapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dawanow.dawanowapi.dto.NearestPharmacyDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PharmacyResponseDTO {
    NearestPharmacyDTO nearestPharmacyDTO;
    private List<MedicinePriceDTO> medicines;
}
