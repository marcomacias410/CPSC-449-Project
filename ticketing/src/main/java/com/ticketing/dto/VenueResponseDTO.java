package com.ticketing.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VenueResponseDTO {
    private Long venueId;
    private String name;
    private String address;
    private String city;
    private Integer totalCapacity;
}
