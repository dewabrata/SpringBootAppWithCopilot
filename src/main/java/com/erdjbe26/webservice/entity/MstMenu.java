package com.erdjbe26.webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "MstMenu")
@Getter
@Setter
public class MstMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Nama", length = 50, nullable = false)
    private String nama;

    @Column(name = "Path", length = 50, nullable = false)
    private String path;

    @Column(name = "Deskripsi", length = 255, nullable = false)
    private String deskripsi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDGroupMenu")
    private MstGroupMenu groupMenu;

    @Column(name = "CreatedBy", nullable = false)
    private Long createdBy;

    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;

    @Column(name = "ModifiedBy")
    private Long modifiedBy;

    @Column(name = "ModifiedDate")
    private LocalDateTime modifiedDate;
}
