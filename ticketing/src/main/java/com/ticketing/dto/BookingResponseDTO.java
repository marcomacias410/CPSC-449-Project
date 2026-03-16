package com.ticketing.dto;

import com.ticketing.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponseDTO {
    private Long bookingId;
    private String bookingReference;
    private LocalDateTime bookingDate;
    private PaymentStatus paymentStatus;
    private String attendeeName;
    private String eventTitle;
    private String ticketTypeName;
    private BigDecimal price;
}
