package com.erdjbe26.webservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierDto {
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String namaSupplier;

    @NotBlank
    @Size(max = 255)
    private String alamatSupplier;
}
