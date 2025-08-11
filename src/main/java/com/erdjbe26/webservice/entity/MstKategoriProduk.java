package com.erdjbe26.webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "MstKategoriProduk")
@Getter
@Setter
public class MstKategoriProduk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NamaProduk", length = 50, nullable = false)
    private String namaProduk;

    @Column(name = "Deskripsi", length = 255, nullable = false)
    private String deskripsi;

    @Column(name = "Notes", length = 255, nullable = false)
    private String notes;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "CreatedBy", nullable = false)
    private Long createdBy;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    @Column(name = "ModifiedBy")
    private Long modifiedBy;
}
