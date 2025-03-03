package org.dawanow.dawanowapi.services;

import org.dawanow.dawanowapi.dto.NearestProviderDTO;

import java.util.List;

public interface PharmacistService {

    List<NearestProviderDTO> getNearestProviders(int pharmacyId, float radius);
}
