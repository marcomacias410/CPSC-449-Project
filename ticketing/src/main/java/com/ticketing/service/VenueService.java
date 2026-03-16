package com.ticketing.service;

import com.ticketing.dto.VenueRequestDTO;
import com.ticketing.dto.VenueResponseDTO;
import com.ticketing.entity.Venue;
import com.ticketing.exception.ResourceNotFoundException;
import com.ticketing.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;

    @Transactional
    public VenueResponseDTO createVenue(VenueRequestDTO dto) {
        Venue venue = Venue.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .city(dto.getCity())
                .totalCapacity(dto.getTotalCapacity())
                .build();
        Venue saved = venueRepository.save(venue);
        return toDTO(saved);
    }

    public Venue findById(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + id));
    }

    private VenueResponseDTO toDTO(Venue v) {
        return VenueResponseDTO.builder()
                .venueId(v.getVenueId())
                .name(v.getName())
                .address(v.getAddress())
                .city(v.getCity())
                .totalCapacity(v.getTotalCapacity())
                .build();
    }
}
