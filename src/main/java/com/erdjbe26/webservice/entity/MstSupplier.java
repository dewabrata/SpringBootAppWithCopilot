package com.erdjbe26.webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "MstSupplier")
@Getter
@Setter
public class MstSupplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NamaSupplier", length = 50, nullable = false)
    private String namaSupplier;

    @Column(name = "AlamatSupplier", length = 255, nullable = false)
    private String alamatSupplier;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "CreatedBy", nullable = false)
    private Long createdBy;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    @Column(name = "ModifiedBy")
    private Long modifiedBy;
}
