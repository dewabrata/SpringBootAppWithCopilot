package com.erdjbe26.webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "MstGroupMenu")
@Getter
@Setter
public class MstGroupMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Nama", length = 50, nullable = false)
    private String nama;

    @Column(name = "Deskripsi", length = 255, nullable = false)
    private String deskripsi;

    @Column(name = "CreatedBy", nullable = false)
    private Long createdBy;

    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;

    @Column(name = "ModifiedBy")
    private Long modifiedBy;

    @Column(name = "ModifiedDate")
    private LocalDateTime modifiedDate;
}
