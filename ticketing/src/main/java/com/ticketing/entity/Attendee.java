package com.ticketing.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "attendee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendeeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "attendee", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
