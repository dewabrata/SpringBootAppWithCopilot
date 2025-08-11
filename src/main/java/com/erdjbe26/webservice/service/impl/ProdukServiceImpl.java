package com.erdjbe26.webservice.service.impl;

import com.erdjbe26.webservice.dto.ProdukDto;
import com.erdjbe26.webservice.entity.MstKategoriProduk;
import com.erdjbe26.webservice.entity.MstProduk;
import com.erdjbe26.webservice.exception.ResourceNotFoundException;
import com.erdjbe26.webservice.mapper.ProdukMapper;
import com.erdjbe26.webservice.repository.MstKategoriProdukRepository;
import com.erdjbe26.webservice.repository.MstProdukRepository;
import com.erdjbe26.webservice.service.ProdukService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdukServiceImpl implements ProdukService {

    private final MstProdukRepository mstProdukRepository;
    private final MstKategoriProdukRepository mstKategoriProdukRepository;
    private final ProdukMapper produkMapper;

    public ProdukServiceImpl(MstProdukRepository mstProdukRepository, MstKategoriProdukRepository mstKategoriProdukRepository, ProdukMapper produkMapper) {
        this.mstProdukRepository = mstProdukRepository;
        this.mstKategoriProdukRepository = mstKategoriProdukRepository;
        this.produkMapper = produkMapper;
    }

    @Override
    @Transactional
    public ProdukDto createProduk(ProdukDto produkDto) {
        MstProduk produk = produkMapper.toEntity(produkDto);
        MstKategoriProduk kategoriProduk = mstKategoriProdukRepository.findById(produkDto.getIdKategoriProduk())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori Produk not found with id: " + produkDto.getIdKategoriProduk()));
        produk.setKategoriProduk(kategoriProduk);
        produk.setCreatedBy(1L); // System user for now
        produk.setCreatedAt(LocalDateTime.now());
        MstProduk savedProduk = mstProdukRepository.save(produk);
        return produkMapper.toDto(savedProduk);
    }

    @Override
    public ProdukDto getProdukById(Long id) {
        MstProduk produk = mstProdukRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produk not found with id: " + id));
        return produkMapper.toDto(produk);
    }

    @Override
    public List<ProdukDto> getAllProduk() {
        return mstProdukRepository.findAll().stream()
                .map(produkMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProdukDto updateProduk(Long id, ProdukDto produkDto) {
        MstProduk existingProduk = mstProdukRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produk not found with id: " + id));

        MstKategoriProduk kategoriProduk = mstKategoriProdukRepository.findById(produkDto.getIdKategoriProduk())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori Produk not found with id: " + produkDto.getIdKategoriProduk()));

        existingProduk.setKategoriProduk(kategoriProduk);
        existingProduk.setNamaProduk(produkDto.getNamaProduk());
        existingProduk.setMerk(produkDto.getMerk());
        existingProduk.setModel(produkDto.getModel());
        existingProduk.setWarna(produkDto.getWarna());
        existingProduk.setDeskripsiProduk(produkDto.getDeskripsiProduk());
        existingProduk.setStok(produkDto.getStok());
        existingProduk.setModifiedBy(1L); // System user for now
        existingProduk.setModifiedAt(LocalDateTime.now());

        MstProduk updatedProduk = mstProdukRepository.save(existingProduk);
        return produkMapper.toDto(updatedProduk);
    }

    @Override
    @Transactional
    public void deleteProduk(Long id) {
        if (!mstProdukRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produk not found with id: " + id);
        }
        mstProdukRepository.deleteById(id);
    }
}
