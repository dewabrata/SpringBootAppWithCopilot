package com.erdjbe26.webservice.service;

import com.erdjbe26.webservice.dto.KategoriProdukDto;
import java.util.List;

public interface KategoriProdukService {
    KategoriProdukDto createKategoriProduk(KategoriProdukDto kategoriProdukDto);
    KategoriProdukDto getKategoriProdukById(Long id);
    List<KategoriProdukDto> getAllKategoriProduk();
    KategoriProdukDto updateKategoriProduk(Long id, KategoriProdukDto kategoriProdukDto);
    void deleteKategoriProduk(Long id);
}
