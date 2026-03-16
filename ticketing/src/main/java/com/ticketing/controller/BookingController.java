package com.ticketing.controller;

import com.ticketing.dto.BookingRequestDTO;
import com.ticketing.dto.BookingResponseDTO;
import com.ticketing.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO dto) {
        BookingResponseDTO response = bookingService.createBooking(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable Long id) {
        BookingResponseDTO response = bookingService.cancelBooking(id);
        return ResponseEntity.ok(response);
    }
}
