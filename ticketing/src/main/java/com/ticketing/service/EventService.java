package com.ticketing.service;

import com.ticketing.dto.*;
import com.ticketing.entity.Event;
import com.ticketing.entity.Organizer;
import com.ticketing.entity.Venue;
import com.ticketing.enums.EventStatus;
import com.ticketing.exception.ResourceNotFoundException;
import com.ticketing.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ticketing.dto.TicketTypeRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final OrganizerService organizerService;
    private final VenueService venueService;

    @Transactional
    public EventResponseDTO createEvent(EventRequestDTO dto) {
        Organizer organizer = organizerService.findById(dto.getOrganizerId());
        Venue venue = venueService.findById(dto.getVenueId());

        Event event = Event.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .status(dto.getStatus() != null ? dto.getStatus() : EventStatus.UPCOMING)
                .organizer(organizer)
                .venue(venue)
                .build();

        Event saved = eventRepository.save(event);
        return toDTO(saved);
    }

    public List<EventResponseDTO> getAllUpcomingEvents() {
        return eventRepository.findByStatus(EventStatus.UPCOMING)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EventResponseDTO getEventById(Long id) {
        Event event = findById(id);
        return toDTO(event);
    }

    public RevenueDTO getEventRevenue(Long id) {
        Event event = findById(id);
        var total = eventRepository.calculateConfirmedRevenueByEventId(id);
        return RevenueDTO.builder()
                .eventId(event.getEventId())
                .eventTitle(event.getTitle())
                .totalConfirmedRevenue(total)
                .build();
    }

    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
    }

    private EventResponseDTO toDTO(Event e) {
        List<TicketTypeRequestDTO> ticketDTOs = e.getTicketTypes() == null ? List.of() :
                e.getTicketTypes().stream()
                        .map(tt -> TicketTypeRequestDTO.builder()
                                .ticketTypeId(tt.getTicketTypeId())
                                .name(tt.getName())
                                .price(tt.getPrice())
                                .quantityAvailable(tt.getQuantityAvailable())
                                .build())
                        .collect(Collectors.toList());

        return EventResponseDTO.builder()
                .eventId(e.getEventId())
                .title(e.getTitle())
                .description(e.getDescription())
                .eventDate(e.getEventDate())
                .status(e.getStatus())
                .organizerName(e.getOrganizer().getName())
                .venueName(e.getVenue().getName())
                .venueCity(e.getVenue().getCity())
                .ticketTypes(ticketDTOs)
                .build();
    }
}
