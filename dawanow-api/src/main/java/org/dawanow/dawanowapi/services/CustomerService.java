package org.dawanow.dawanowapi.services;


import org.dawanow.dawanowapi.dto.NearestPharmacyDTO;

import java.util.List;

public interface CustomerService {



    List<NearestPharmacyDTO> getNearestPharmacies(int requestId, float radius );
}
