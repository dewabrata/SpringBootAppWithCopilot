package com.erdjbe26.webservice.dto;

import java.time.LocalDateTime;

/**
 * DTO for LogKategoriProduk entity
 */
public record LogKategoriProdukDto(
        Long id,
        Long idKategoriProduk,
        String namaProduk,
        String deskripsi,
        String notes,
        Character flag,
        LocalDateTime createdAt,
        Long createdBy
) {
}