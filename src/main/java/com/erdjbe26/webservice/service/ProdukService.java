package com.erdjbe26.webservice.service;

import com.erdjbe26.webservice.dto.ProdukDto;
import java.util.List;

public interface ProdukService {
    ProdukDto createProduk(ProdukDto produkDto);
    ProdukDto getProdukById(Long id);
    List<ProdukDto> getAllProduk();
    ProdukDto updateProduk(Long id, ProdukDto produkDto);
    void deleteProduk(Long id);
}
