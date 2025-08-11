package com.erdjbe26.webservice.dto;

import java.time.LocalDateTime;

/**
 * DTO for LogProduk entity
 */
public record LogProdukDto(
        Long id,
        Long idProduk,
        Long idKategoriProduk,
        String namaProduk,
        String merk,
        String model,
        String warna,
        String deskripsiProduk,
        Integer stok,
        Character flag,
        LocalDateTime createdAt,
        Long createdBy
) {
}