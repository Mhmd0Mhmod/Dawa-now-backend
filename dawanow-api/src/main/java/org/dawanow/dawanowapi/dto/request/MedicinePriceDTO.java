package org.dawanow.dawanowapi.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dawanow.dawanowapi.models.Request;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicinePriceDTO {
    private String medicine;
    private int quantity;
    private double price;

    public static List<MedicinePriceDTO> getRequestDataAsObject(Request request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(request.getRequestData(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, MedicinePriceDTO.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse request data", e);
        }
    }

}
