package com.ticketing.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RevenueDTO {
    private Long eventId;
    private String eventTitle;
    private BigDecimal totalConfirmedRevenue;
}
