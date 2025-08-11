package com.erdjbe26.webservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProdukDto {
    private Long id;

    @NotNull
    private Long idKategoriProduk;

    private String namaKategoriProduk;

    @NotBlank
    @Size(max = 50)
    private String namaProduk;

    @NotBlank
    @Size(max = 50)
    private String merk;

    @NotBlank
    @Size(max = 50)
    private String model;

    @NotBlank
    @Size(max = 30)
    private String warna;

    @NotBlank
    @Size(max = 255)
    private String deskripsiProduk;

    @NotNull
    @Min(0)
    private Integer stok;
}
