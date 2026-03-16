package com.ticketing.controller;

import com.ticketing.dto.EventRequestDTO;
import com.ticketing.dto.EventResponseDTO;
import com.ticketing.dto.RevenueDTO;
import com.ticketing.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventRequestDTO dto) {
        EventResponseDTO response = eventService.createEvent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllUpcomingEvents() {
        return ResponseEntity.ok(eventService.getAllUpcomingEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @GetMapping("/{id}/revenue")
    public ResponseEntity<RevenueDTO> getEventRevenue(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventRevenue(id));
    }
}
