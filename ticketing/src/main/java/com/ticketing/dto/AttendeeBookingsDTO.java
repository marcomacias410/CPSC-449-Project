package com.ticketing.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AttendeeBookingsDTO {
    private Long attendeeId;
    private String attendeeName;
    private String email;
    private List<BookingResponseDTO> bookings;
}
