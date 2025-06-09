package com.starchenko.hotelmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String type;
    @Column(name = "price_per_night")
    private Double pricePerNight;
    @Column(name = "is_available")
    private Boolean isAvailable;
}