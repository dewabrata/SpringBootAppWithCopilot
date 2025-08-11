package com.erdjbe26.webservice.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String namaLengkap;
    private String email;
    private String noHp;
    private String alamat;
    private LocalDate tanggalLahir;
    private Boolean isRegistered;
    private Long idAkses;
    private String namaAkses;
}
