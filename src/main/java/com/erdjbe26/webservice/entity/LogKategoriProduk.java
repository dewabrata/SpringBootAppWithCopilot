package com.erdjbe26.webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "LogKategoriProduk", indexes = {
    @Index(name = "IX_LogKategoriProduk_Waktu", columnList = "IDKategoriProduk, CreatedAt")
})
@Getter
@Setter
public class LogKategoriProduk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDKategoriProduk")
    private Long idKategoriProduk;

    @Column(name = "NamaProduk", length = 50, nullable = false)
    private String namaProduk;

    @Column(name = "Deskripsi", length = 255, nullable = false)
    private String deskripsi;

    @Column(name = "Notes", length = 255, nullable = false)
    private String notes;

    @Column(name = "Flag", length = 1, nullable = false)
    private Character flag;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "CreatedBy", nullable = false)
    private Long createdBy;
}
