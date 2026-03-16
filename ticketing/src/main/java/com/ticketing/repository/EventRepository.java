package com.ticketing.repository;

import com.ticketing.entity.Event;
import com.ticketing.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByStatus(EventStatus status);

    @Query("""
        SELECT COALESCE(SUM(tt.price), 0)
        FROM Booking b
        JOIN b.ticketType tt
        JOIN tt.event e
        WHERE e.eventId = :eventId
        AND b.paymentStatus = com.ticketing.enums.PaymentStatus.CONFIRMED
        """)
    BigDecimal calculateConfirmedRevenueByEventId(@Param("eventId") Long eventId);
}
