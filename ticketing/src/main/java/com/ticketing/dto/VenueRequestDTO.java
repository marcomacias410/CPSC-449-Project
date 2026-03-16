package com.ticketing.dto;

import lombok.Data;

@Data
public class VenueRequestDTO {
    private String name;
    private String address;
    private String city;
    private Integer totalCapacity;
}
