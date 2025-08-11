package com.erdjbe26.webservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterUserDto {

    @NotBlank
    @Size(min = 4, max = 16)
    private String username;

    @NotBlank
    @Size(min = 6, max = 64)
    private String password;

    @NotBlank
    @Size(max = 70)
    private String namaLengkap;

    @NotBlank
    @Email
    @Size(max = 256)
    private String email;

    @NotBlank
    @Size(max = 18)
    private String noHp;

    @NotBlank
    @Size(max = 255)
    private String alamat;

    @NotNull
    private LocalDate tanggalLahir;

    @NotNull
    private Long idAkses;
}
