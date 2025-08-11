package com.erdjbe26.webservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class KategoriProdukDto {
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String namaProduk; // This is category name

    @NotBlank
    @Size(max = 255)
    private String deskripsi;

    @NotBlank
    @Size(max = 255)
    private String notes;
}
