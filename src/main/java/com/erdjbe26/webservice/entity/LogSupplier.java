package com.erdjbe26.webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "LogSupplier", indexes = {
    @Index(name = "IX_LogSupplier_Waktu", columnList = "IDSupplier, CreatedAt")
})
@Getter
@Setter
public class LogSupplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDSupplier")
    private Long idSupplier;

    @Column(name = "NamaSupplier", length = 50, nullable = false)
    private String namaSupplier;

    @Column(name = "AlamatSupplier", length = 255, nullable = false)
    private String alamatSupplier;

    @Column(name = "Flag", length = 1, nullable = false)
    private Character flag;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "CreatedBy", nullable = false)
    private Long createdBy;
}
