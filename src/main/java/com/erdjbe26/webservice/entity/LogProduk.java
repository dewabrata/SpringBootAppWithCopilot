package com.erdjbe26.webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "LogProduk", indexes = {
    @Index(name = "IX_LogProduk_ProdukWaktu", columnList = "IDProduk, CreatedAt")
})
@Getter
@Setter
public class LogProduk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDProduk")
    private Long idProduk;

    @Column(name = "IDKategoriProduk", nullable = false)
    private Long idKategoriProduk;

    @Column(name = "NamaProduk", length = 50, nullable = false)
    private String namaProduk;

    @Column(name = "Merk", length = 50, nullable = false)
    private String merk;

    @Column(name = "Model", length = 50, nullable = false)
    private String model;

    @Column(name = "Warna", length = 30, nullable = false)
    private String warna;

    @Column(name = "DeskripsiProduk", length = 255, nullable = false)
    private String deskripsiProduk;

    @Column(name = "Stok", nullable = false)
    private Integer stok;

    @Column(name = "Flag", length = 1, nullable = false)
    private Character flag;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "CreatedBy", nullable = false)
    private Long createdBy;
}
