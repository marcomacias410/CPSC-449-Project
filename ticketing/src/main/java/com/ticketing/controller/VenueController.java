package com.ticketing.controller;

import com.ticketing.dto.VenueRequestDTO;
import com.ticketing.dto.VenueResponseDTO;
import com.ticketing.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    @PostMapping
    public ResponseEntity<VenueResponseDTO> createVenue(@RequestBody VenueRequestDTO dto) {
        VenueResponseDTO response = venueService.createVenue(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
