package com.erdjbe26.webservice.service.impl;

import com.erdjbe26.webservice.dto.AksesDto;
import com.erdjbe26.webservice.entity.MstAkses;
import com.erdjbe26.webservice.exception.ResourceNotFoundException;
import com.erdjbe26.webservice.mapper.AksesMapper;
import com.erdjbe26.webservice.repository.MstAksesRepository;
import com.erdjbe26.webservice.service.AksesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AksesServiceImpl implements AksesService {

    private final MstAksesRepository mstAksesRepository;
    private final AksesMapper aksesMapper;

    public AksesServiceImpl(MstAksesRepository mstAksesRepository, AksesMapper aksesMapper) {
        this.mstAksesRepository = mstAksesRepository;
        this.aksesMapper = aksesMapper;
    }

    @Override
    @Transactional
    public AksesDto createAkses(AksesDto aksesDto) {
        MstAkses akses = aksesMapper.toEntity(aksesDto);
        akses.setCreatedBy(1L); // System user for now
        akses.setCreatedDate(LocalDateTime.now());
        MstAkses savedAkses = mstAksesRepository.save(akses);
        return aksesMapper.toDto(savedAkses);
    }

    @Override
    public AksesDto getAksesById(Long id) {
        MstAkses akses = mstAksesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Akses not found with id: " + id));
        return aksesMapper.toDto(akses);
    }

    @Override
    public List<AksesDto> getAllAkses() {
        return mstAksesRepository.findAll().stream()
                .map(aksesMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AksesDto updateAkses(Long id, AksesDto aksesDto) {
        MstAkses existingAkses = mstAksesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Akses not found with id: " + id));

        existingAkses.setNama(aksesDto.getNama());
        existingAkses.setDeskripsi(aksesDto.getDeskripsi());
        existingAkses.setModifiedBy(1L); // System user for now
        existingAkses.setModifiedDate(LocalDateTime.now());

        MstAkses updatedAkses = mstAksesRepository.save(existingAkses);
        return aksesMapper.toDto(updatedAkses);
    }

    @Override
    @Transactional
    public void deleteAkses(Long id) {
        if (!mstAksesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Akses not found with id: " + id);
        }
        mstAksesRepository.deleteById(id);
    }
}
