package com.erdjbe26.webservice.dto;

import java.time.LocalDateTime;

/**
 * DTO for LogSupplier entity
 */
public record LogSupplierDto(
        Long id,
        Long idSupplier,
        String namaSupplier,
        String alamatSupplier,
        Character flag,
        LocalDateTime createdAt,
        Long createdBy
) {
}