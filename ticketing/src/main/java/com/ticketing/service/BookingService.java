package com.ticketing.service;

import com.ticketing.dto.BookingRequestDTO;
import com.ticketing.dto.BookingResponseDTO;
import com.ticketing.entity.Attendee;
import com.ticketing.entity.Booking;
import com.ticketing.entity.TicketType;
import com.ticketing.enums.PaymentStatus;
import com.ticketing.exception.BadRequestException;
import com.ticketing.exception.ResourceNotFoundException;
import com.ticketing.repository.BookingRepository;
import com.ticketing.repository.TicketTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final AttendeeService attendeeService;

    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO dto) {
        // 1. Check ticket type exists and has availability
        TicketType ticketType = ticketTypeRepository.findById(dto.getTicketTypeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ticket type not found with id: " + dto.getTicketTypeId()));

        if (ticketType.getQuantityAvailable() <= 0) {
            throw new BadRequestException("Sorry, this ticket type is sold out.");
        }

        // 2. Check attendee has not already booked this ticket type
        Attendee attendee = attendeeService.findById(dto.getAttendeeId());

        boolean alreadyBooked = bookingRepository.existsByAttendee_AttendeeIdAndTicketType_TicketTypeId(
                attendee.getAttendeeId(), ticketType.getTicketTypeId());

        if (alreadyBooked) {
            throw new BadRequestException("You have already booked this ticket type.");
        }

        // 3. Decrement available quantity
        ticketType.setQuantityAvailable(ticketType.getQuantityAvailable() - 1);
        ticketTypeRepository.save(ticketType);

        // 4 & 5. Create booking with auto timestamp and CONFIRMED status
        Booking booking = Booking.builder()
                .bookingDate(LocalDateTime.now())
                .paymentStatus(PaymentStatus.CONFIRMED)
                .attendee(attendee)
                .ticketType(ticketType)
                // Temporary reference — updated after we have the generated ID
                .bookingReference("PENDING")
                .build();

        Booking saved = bookingRepository.save(booking);

        // 4. Generate unique booking reference in format TKT-{year}-{zero-padded id}
        String reference = String.format("TKT-%d-%05d",
                saved.getBookingDate().getYear(),
                saved.getBookingId());
        saved.setBookingReference(reference);
        saved = bookingRepository.save(saved);

        return toDTO(saved);
    }

    @Transactional
    public BookingResponseDTO cancelBooking(Long bookingId) {
        // 1. Verify booking exists
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        // 1b. Verify it is not already cancelled
        if (booking.getPaymentStatus() == PaymentStatus.CANCELLED) {
            throw new BadRequestException("This booking is already cancelled.");
        }

        // 2. Set status to CANCELLED
        booking.setPaymentStatus(PaymentStatus.CANCELLED);

        // 3. Restore ticket inventory
        TicketType ticketType = booking.getTicketType();
        ticketType.setQuantityAvailable(ticketType.getQuantityAvailable() + 1);
        ticketTypeRepository.save(ticketType);

        Booking updated = bookingRepository.save(booking);
        return toDTO(updated);
    }

    private BookingResponseDTO toDTO(Booking b) {
        return BookingResponseDTO.builder()
                .bookingId(b.getBookingId())
                .bookingReference(b.getBookingReference())
                .bookingDate(b.getBookingDate())
                .paymentStatus(b.getPaymentStatus())
                .attendeeName(b.getAttendee().getName())
                .eventTitle(b.getTicketType().getEvent().getTitle())
                .ticketTypeName(b.getTicketType().getName())
                .price(b.getTicketType().getPrice())
                .build();
    }
}
