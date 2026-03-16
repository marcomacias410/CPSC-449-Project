package com.ticketing.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizerResponseDTO {
    private Long organizerId;
    private String name;
    private String email;
    private String phone;
}
