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

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public String getNumber() {
        return number;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }


    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    public Boolean getIsAvailable() {
        return isAvailable;
    }
}