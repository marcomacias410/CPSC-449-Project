package com.ticketing.repository;

import com.ticketing.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByAttendee_AttendeeIdAndTicketType_TicketTypeId(
            Long attendeeId, Long ticketTypeId);

    List<Booking> findByAttendee_AttendeeId(Long attendeeId);
}
