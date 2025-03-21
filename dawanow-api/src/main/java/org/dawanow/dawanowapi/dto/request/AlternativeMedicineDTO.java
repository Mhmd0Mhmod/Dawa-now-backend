package org.dawanow.dawanowapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeMedicineDTO {
    private Long id;
    private String medicine;
    private int quantity;
    private double price;
    private boolean chosen;
    private boolean isTape;
}
