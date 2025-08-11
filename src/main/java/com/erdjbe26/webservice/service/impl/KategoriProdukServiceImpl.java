package com.erdjbe26.webservice.service.impl;

import com.erdjbe26.webservice.dto.KategoriProdukDto;
import com.erdjbe26.webservice.entity.MstKategoriProduk;
import com.erdjbe26.webservice.exception.ResourceNotFoundException;
import com.erdjbe26.webservice.mapper.KategoriProdukMapper;
import com.erdjbe26.webservice.repository.MstKategoriProdukRepository;
import com.erdjbe26.webservice.service.KategoriProdukService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KategoriProdukServiceImpl implements KategoriProdukService {

    private final MstKategoriProdukRepository mstKategoriProdukRepository;
    private final KategoriProdukMapper kategoriProdukMapper;

    public KategoriProdukServiceImpl(MstKategoriProdukRepository mstKategoriProdukRepository, KategoriProdukMapper kategoriProdukMapper) {
        this.mstKategoriProdukRepository = mstKategoriProdukRepository;
        this.kategoriProdukMapper = kategoriProdukMapper;
    }

    @Override
    @Transactional
    public KategoriProdukDto createKategoriProduk(KategoriProdukDto kategoriProdukDto) {
        MstKategoriProduk kategoriProduk = kategoriProdukMapper.toEntity(kategoriProdukDto);
        kategoriProduk.setCreatedBy(1L); // System user for now
        kategoriProduk.setCreatedAt(LocalDateTime.now());
        MstKategoriProduk savedKategoriProduk = mstKategoriProdukRepository.save(kategoriProduk);
        return kategoriProdukMapper.toDto(savedKategoriProduk);
    }

    @Override
    public KategoriProdukDto getKategoriProdukById(Long id) {
        MstKategoriProduk kategoriProduk = mstKategoriProdukRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori Produk not found with id: " + id));
        return kategoriProdukMapper.toDto(kategoriProduk);
    }

    @Override
    public List<KategoriProdukDto> getAllKategoriProduk() {
        return mstKategoriProdukRepository.findAll().stream()
                .map(kategoriProdukMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public KategoriProdukDto updateKategoriProduk(Long id, KategoriProdukDto kategoriProdukDto) {
        MstKategoriProduk existingKategoriProduk = mstKategoriProdukRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori Produk not found with id: " + id));

        existingKategoriProduk.setNamaProduk(kategoriProdukDto.getNamaProduk());
        existingKategoriProduk.setDeskripsi(kategoriProdukDto.getDeskripsi());
        existingKategoriProduk.setNotes(kategoriProdukDto.getNotes());
        existingKategoriProduk.setModifiedBy(1L); // System user for now
        existingKategoriProduk.setModifiedAt(LocalDateTime.now());

        MstKategoriProduk updatedKategoriProduk = mstKategoriProdukRepository.save(existingKategoriProduk);
        return kategoriProdukMapper.toDto(updatedKategoriProduk);
    }

    @Override
    @Transactional
    public void deleteKategoriProduk(Long id) {
        if (!mstKategoriProdukRepository.existsById(id)) {
            throw new ResourceNotFoundException("Kategori Produk not found with id: " + id);
        }
        mstKategoriProdukRepository.deleteById(id);
    }
}
