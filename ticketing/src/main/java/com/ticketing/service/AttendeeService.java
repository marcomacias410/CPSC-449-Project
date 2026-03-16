package com.ticketing.service;

import com.ticketing.dto.AttendeeBookingsDTO;
import com.ticketing.dto.AttendeeRequestDTO;
import com.ticketing.dto.BookingResponseDTO;
import com.ticketing.dto.OrganizerResponseDTO;
import com.ticketing.entity.Attendee;
import com.ticketing.entity.Booking;
import com.ticketing.exception.BadRequestException;
import com.ticketing.exception.ResourceNotFoundException;
import com.ticketing.repository.AttendeeRepository;
import com.ticketing.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public Attendee createAttendee(AttendeeRequestDTO dto) {
        if (attendeeRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("An attendee with this email already exists: " + dto.getEmail());
        }
        Attendee attendee = Attendee.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
        return attendeeRepository.save(attendee);
    }

    public AttendeeBookingsDTO getAttendeeBookings(Long attendeeId) {
        Attendee attendee = findById(attendeeId);
        List<Booking> bookings = bookingRepository.findByAttendee_AttendeeId(attendeeId);

        List<BookingResponseDTO> bookingDTOs = bookings.stream()
                .map(b -> BookingResponseDTO.builder()
                        .bookingId(b.getBookingId())
                        .bookingReference(b.getBookingReference())
                        .bookingDate(b.getBookingDate())
                        .paymentStatus(b.getPaymentStatus())
                        .attendeeName(attendee.getName())
                        .eventTitle(b.getTicketType().getEvent().getTitle())
                        .ticketTypeName(b.getTicketType().getName())
                        .price(b.getTicketType().getPrice())
                        .build())
                .collect(Collectors.toList());

        return AttendeeBookingsDTO.builder()
                .attendeeId(attendee.getAttendeeId())
                .attendeeName(attendee.getName())
                .email(attendee.getEmail())
                .bookings(bookingDTOs)
                .build();
    }

    public Attendee findById(Long id) {
        return attendeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendee not found with id: " + id));
    }
}
