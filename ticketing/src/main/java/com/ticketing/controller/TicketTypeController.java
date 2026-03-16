package com.ticketing.controller;

import com.ticketing.dto.TicketTypeRequestDTO;
import com.ticketing.entity.Event;
import com.ticketing.entity.TicketType;
import com.ticketing.repository.TicketTypeRepository;
import com.ticketing.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket-types")
@RequiredArgsConstructor
public class TicketTypeController {

    private final TicketTypeRepository ticketTypeRepository;
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<TicketTypeRequestDTO> createTicketType(@RequestBody TicketTypeRequestDTO dto) {
        Event event = eventService.findById(dto.getEventId());
        TicketType tt = TicketType.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .quantityAvailable(dto.getQuantityAvailable())
                .event(event)
                .build();
        TicketType saved = ticketTypeRepository.save(tt);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                TicketTypeRequestDTO.builder()
                        .ticketTypeId(saved.getTicketTypeId())
                        .name(saved.getName())
                        .price(saved.getPrice())
                        .quantityAvailable(saved.getQuantityAvailable())
                        .build()
        );
    }
}