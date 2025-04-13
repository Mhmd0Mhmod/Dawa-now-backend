package org.dawanow.dawanowapi.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trade_name", length = 100)
    private String tradeName;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "category", length = 100, nullable = false)
    private String category;

    @Column(name = "form", length = 100)
    private String form;

    @Column(name = "active_ingredient", columnDefinition = "TEXT")
    private String activeIngredient;

    @Column(name = "img_url", columnDefinition = "TEXT")
    private String imgUrl;
}
