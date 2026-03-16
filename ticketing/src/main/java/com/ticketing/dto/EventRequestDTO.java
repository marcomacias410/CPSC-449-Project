package com.ticketing.dto;

import com.ticketing.enums.EventStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequestDTO {
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private EventStatus status;
    private Long organizerId;
    private Long venueId;
}
