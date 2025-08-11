package com.erdjbe26.webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "MstProduk",
    indexes = {
        @Index(name = "IX_MstProduk_IDKategoriProduk", columnList = "IDKategoriProduk")
    }
)
@Getter
@Setter
public class MstProduk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDKategoriProduk", nullable = false)
    private MstKategoriProduk kategoriProduk;

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

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "CreatedBy", nullable = false)
    private Long createdBy;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    @Column(name = "ModifiedBy")
    private Long modifiedBy;
}
