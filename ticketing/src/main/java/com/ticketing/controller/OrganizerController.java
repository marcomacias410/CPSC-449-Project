package com.ticketing.controller;

import com.ticketing.dto.OrganizerRequestDTO;
import com.ticketing.dto.OrganizerResponseDTO;
import com.ticketing.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizers")
@RequiredArgsConstructor
public class OrganizerController {

    private final OrganizerService organizerService;

    @PostMapping
    public ResponseEntity<OrganizerResponseDTO> createOrganizer(@RequestBody OrganizerRequestDTO dto) {
        OrganizerResponseDTO response = organizerService.createOrganizer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
