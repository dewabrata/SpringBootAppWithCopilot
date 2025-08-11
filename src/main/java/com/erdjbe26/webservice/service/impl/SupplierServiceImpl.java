package com.erdjbe26.webservice.service.impl;

import com.erdjbe26.webservice.dto.SupplierDto;
import com.erdjbe26.webservice.entity.MstSupplier;
import com.erdjbe26.webservice.exception.ResourceNotFoundException;
import com.erdjbe26.webservice.mapper.SupplierMapper;
import com.erdjbe26.webservice.repository.MstSupplierRepository;
import com.erdjbe26.webservice.service.SupplierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final MstSupplierRepository mstSupplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierServiceImpl(MstSupplierRepository mstSupplierRepository, SupplierMapper supplierMapper) {
        this.mstSupplierRepository = mstSupplierRepository;
        this.supplierMapper = supplierMapper;
    }

    @Override
    @Transactional
    public SupplierDto createSupplier(SupplierDto supplierDto) {
        MstSupplier supplier = supplierMapper.toEntity(supplierDto);
        supplier.setCreatedBy(1L); // System user for now
        supplier.setCreatedAt(LocalDateTime.now());
        MstSupplier savedSupplier = mstSupplierRepository.save(supplier);
        return supplierMapper.toDto(savedSupplier);
    }

    @Override
    public SupplierDto getSupplierById(Long id) {
        MstSupplier supplier = mstSupplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        return supplierMapper.toDto(supplier);
    }

    @Override
    public List<SupplierDto> getAllSuppliers() {
        return mstSupplierRepository.findAll().stream()
                .map(supplierMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SupplierDto updateSupplier(Long id, SupplierDto supplierDto) {
        MstSupplier existingSupplier = mstSupplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));

        existingSupplier.setNamaSupplier(supplierDto.getNamaSupplier());
        existingSupplier.setAlamatSupplier(supplierDto.getAlamatSupplier());
        existingSupplier.setModifiedBy(1L); // System user for now
        existingSupplier.setModifiedAt(LocalDateTime.now());

        MstSupplier updatedSupplier = mstSupplierRepository.save(existingSupplier);
        return supplierMapper.toDto(updatedSupplier);
    }

    @Override
    @Transactional
    public void deleteSupplier(Long id) {
        if (!mstSupplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + id);
        }
        mstSupplierRepository.deleteById(id);
    }
}
