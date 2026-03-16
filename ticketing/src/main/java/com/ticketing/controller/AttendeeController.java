package com.ticketing.controller;

import com.ticketing.dto.AttendeeBookingsDTO;
import com.ticketing.dto.AttendeeRequestDTO;
import com.ticketing.entity.Attendee;
import com.ticketing.service.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService attendeeService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registerAttendee(@RequestBody AttendeeRequestDTO dto) {
        Attendee saved = attendeeService.createAttendee(dto);
        Map<String, Object> response = Map.of(
                "attendeeId", saved.getAttendeeId(),
                "name", saved.getName(),
                "email", saved.getEmail()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/bookings")
    public ResponseEntity<AttendeeBookingsDTO> getAttendeeBookings(@PathVariable Long id) {
        return ResponseEntity.ok(attendeeService.getAttendeeBookings(id));
    }
}
