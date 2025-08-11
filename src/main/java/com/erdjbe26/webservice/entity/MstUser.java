package com.erdjbe26.webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "MstUser", uniqueConstraints = {
        @UniqueConstraint(columnNames = "Username"),
        @UniqueConstraint(columnNames = "Email")
})
@Getter
@Setter
public class MstUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Username", length = 16, nullable = false)
    private String username;

    @Column(name = "Password", length = 64, nullable = false)
    private String password;

    @Column(name = "NamaLengkap", length = 70, nullable = false)
    private String namaLengkap;

    @Column(name = "Email", length = 256, nullable = false)
    private String email;

    @Column(name = "NoHp", length = 18, nullable = false)
    private String noHp;

    @Column(name = "Alamat", length = 255, nullable = false)
    private String alamat;

    @Column(name = "TanggalLahir", nullable = false)
    private LocalDate tanggalLahir;

    @Column(name = "IsRegistered")
    private Boolean isRegistered;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDAkses")
    private MstAkses akses;

    @Column(name = "OTP", length = 64)
    private String otp;

    @Column(name = "TokenEstafet", length = 64)
    private String tokenEstafet;

    @Column(name = "LinkImage", length = 256)
    private String linkImage;

    @Column(name = "PathImage", length = 256)
    private String pathImage;

    @Column(name = "CreatedBy", nullable = false)
    private Long createdBy;

    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;

    @Column(name = "ModifiedBy")
    private Long modifiedBy;

    @Column(name = "ModifiedDate")
    private LocalDateTime modifiedDate;
}
