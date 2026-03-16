package com.ticketing.service;

import com.ticketing.dto.OrganizerRequestDTO;
import com.ticketing.dto.OrganizerResponseDTO;
import com.ticketing.entity.Organizer;
import com.ticketing.exception.ResourceNotFoundException;
import com.ticketing.repository.OrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrganizerService {

    private final OrganizerRepository organizerRepository;

    @Transactional
    public OrganizerResponseDTO createOrganizer(OrganizerRequestDTO dto) {
        Organizer organizer = Organizer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
        Organizer saved = organizerRepository.save(organizer);
        return toDTO(saved);
    }

    public Organizer findById(Long id) {
        return organizerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found with id: " + id));
    }

    private OrganizerResponseDTO toDTO(Organizer o) {
        return OrganizerResponseDTO.builder()
                .organizerId(o.getOrganizerId())
                .name(o.getName())
                .email(o.getEmail())
                .phone(o.getPhone())
                .build();
    }
}
