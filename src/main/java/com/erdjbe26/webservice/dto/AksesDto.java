package com.erdjbe26.webservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AksesDto {
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String nama;

    @NotBlank
    @Size(max = 255)
    private String deskripsi;
}
