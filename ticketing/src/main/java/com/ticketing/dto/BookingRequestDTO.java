package com.ticketing.dto;

import lombok.Data;

@Data
public class BookingRequestDTO {
    private Long attendeeId;
    private Long ticketTypeId;
}
