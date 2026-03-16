package com.ticketing.dto;

import com.ticketing.enums.EventStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EventResponseDTO {
    private Long eventId;
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private EventStatus status;
    private String organizerName;
    private String venueName;
    private String venueCity;
    private List<TicketTypeRequestDTO> ticketTypes;
}
