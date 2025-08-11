
# Database Design — ERDJBE26 (MySQL 8.0)

Dokumen ini adalah adaptasi dari desain sebelumnya (SQL Server) ke **MySQL 8.0**, lengkap dengan struktur tabel, penjelasan kolom, relasi, serta DDL siap eksekusi.

---

## 1) Prinsip Desain & Konvensi (MySQL)
- **DBMS**: MySQL 8.0, **InnoDB**, **utf8mb4** (unicode, emoji-safe).
- **Penamaan**: 
  - Tabel master: `Mst*`, tabel mapping: `Map*`, tabel log: `Log*`.
  - PK tunggal bernama `ID` bertipe `BIGINT UNSIGNED AUTO_INCREMENT`.
  - FK menggunakan awalan `ID*` bertipe `BIGINT UNSIGNED`.
- **Audit Trail**: umum menggunakan `CreatedAt/CreatedDate`, `CreatedBy`, `ModifiedAt/ModifiedDate`, `ModifiedBy` (tipe `DATETIME(6)` untuk microseconds).
- **BOOLEAN**: gunakan `TINYINT(1)` (alias `BOOLEAN`) di MySQL.
- **Indexing**: 
  - PK = clustered index (InnoDB).
  - Buat indeks pada semua kolom FK.
  - Unique index untuk `Username`, `Email`.
- **Engine & Charset Default**: semua tabel: `ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci`.
- **Datetime Default**: bila perlu, gunakan `DEFAULT CURRENT_TIMESTAMP(6)` / `ON UPDATE CURRENT_TIMESTAMP(6)` (tidak dipaksakan di bawah agar netral terhadap aplikasi).
- **Kardinalitas & Aksi FK**: relasi menggunakan `RESTRICT` (default). Bila butuh cascade, modifikasi sesuai kebutuhan bisnis.

---

## 2) Daftar Tabel & Struktur + Relasi Singkat

### 2.1 MstAkses
Master role/akses.
- Relasi: **1..N** ke `MstUser` (`MstUser.IDAkses`), **N..N** ke `MstMenu` via `MapAksesMenu`.

### 2.2 MstGroupMenu
Kelompok menu.
- Relasi: **1..N** ke `MstMenu` (opsional/nullable).

### 2.3 MstMenu
Master menu (route/URL).
- Relasi: **N..N** ke `MstAkses` via `MapAksesMenu`.

### 2.4 MapAksesMenu
Mapping **many-to-many** akses—menu.
- PK komposit `(IDAkses, IDMenu)`.

### 2.5 MstUser
Master pengguna.
- Unique: `Username`, `Email`.
- Relasi: **N..1** ke `MstAkses` (nullable untuk user tanpa role).

### 2.6 MstKategoriProduk
Master kategori produk.
- Relasi: **1..N** ke `MstProduk`.

### 2.7 MstProduk
Master produk.
- Relasi: **N..1** ke `MstKategoriProduk`; **N..N** ke `MstSupplier` via `MapProdukSupplier`.

### 2.8 MstSupplier
Master supplier.
- Relasi: **N..N** ke `MstProduk` via `MapProdukSupplier`.

### 2.9 MapProdukSupplier
Mapping **many-to-many** produk—supplier.
- PK komposit `(IDProduk, IDSupplier)`.

### 2.10 LogProduk, 2.11 LogKategoriProduk, 2.12 LogSupplier
Table log menyimpan snapshot data + `Flag` (`I/U/D`) dan metadata pembuat/waktu.

---

## 3) DDL MySQL 8.0

> Catatan:
> - Semua tabel menggunakan `InnoDB` dan `utf8mb4`.
> - `BOOLEAN` = `TINYINT(1)`.
> - Gunakan backtick untuk identifier yang berpotensi bentrok (mis. `Path`, `Password`).

```sql
-- =====================================================
-- Session Defaults (opsional)
-- =====================================================
SET NAMES utf8mb4 COLLATE utf8mb4_0900_ai_ci;
SET SESSION sql_mode = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- =====================================================
-- MASTER & SECURITY
-- =====================================================
CREATE TABLE `MstAkses` (
  `ID`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Nama`          VARCHAR(50)  NOT NULL,
  `Deskripsi`     VARCHAR(255) NOT NULL,
  `CreatedBy`     BIGINT UNSIGNED NOT NULL,
  `CreatedDate`   DATETIME(6) NULL,
  `ModifiedBy`    BIGINT UNSIGNED NULL,
  `ModifiedDate`  DATETIME(6) NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_MstAkses_Nama` (`Nama`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `MstGroupMenu` (
  `ID`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Nama`          VARCHAR(50)  NOT NULL,
  `Deskripsi`     VARCHAR(255) NOT NULL,
  `CreatedBy`     BIGINT UNSIGNED NOT NULL,
  `CreatedDate`   DATETIME(6) NULL,
  `ModifiedBy`    BIGINT UNSIGNED NULL,
  `ModifiedDate`  DATETIME(6) NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `MstMenu` (
  `ID`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Nama`          VARCHAR(50)  NOT NULL,
  `Path`          VARCHAR(50)  NOT NULL,
  `Deskripsi`     VARCHAR(255) NOT NULL,
  `IDGroupMenu`   BIGINT UNSIGNED NULL,
  `CreatedBy`     BIGINT UNSIGNED NOT NULL,
  `CreatedDate`   DATETIME(6) NULL,
  `ModifiedBy`    BIGINT UNSIGNED NULL,
  `ModifiedDate`  DATETIME(6) NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_MstMenu_IDGroupMenu` (`IDGroupMenu`),
  CONSTRAINT `FK_MstMenu_MstGroupMenu`
    FOREIGN KEY (`IDGroupMenu`) REFERENCES `MstGroupMenu` (`ID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `MapAksesMenu` (
  `IDAkses` BIGINT UNSIGNED NOT NULL,
  `IDMenu`  BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`IDAkses`, `IDMenu`),
  KEY `IX_MapAksesMenu_IDMenu` (`IDMenu`),
  CONSTRAINT `FK_MapAksesMenu_MstAkses`
    FOREIGN KEY (`IDAkses`) REFERENCES `MstAkses` (`ID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_MapAksesMenu_MstMenu`
    FOREIGN KEY (`IDMenu`)  REFERENCES `MstMenu` (`ID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `MstUser` (
  `ID`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Username`      VARCHAR(16)  NOT NULL,
  `Password`      VARCHAR(64)  NOT NULL,
  `NamaLengkap`   VARCHAR(70)  NOT NULL,
  `Email`         VARCHAR(256) NOT NULL,
  `NoHp`          VARCHAR(18)  NOT NULL,
  `Alamat`        VARCHAR(255) NOT NULL,
  `TanggalLahir`  DATE         NOT NULL,
  `IsRegistered`  TINYINT(1)   NULL,
  `IDAkses`       BIGINT UNSIGNED NULL,
  `OTP`           VARCHAR(64)  NULL,
  `TokenEstafet`  VARCHAR(64)  NULL,
  `LinkImage`     VARCHAR(256) NULL,
  `PathImage`     VARCHAR(256) NULL,
  `CreatedBy`     BIGINT UNSIGNED NOT NULL,
  `CreatedDate`   DATETIME(6) NULL,
  `ModifiedBy`    BIGINT UNSIGNED NULL,
  `ModifiedDate`  DATETIME(6) NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_MstUser_Username` (`Username`),
  UNIQUE KEY `UQ_MstUser_Email`    (`Email`),
  KEY `IX_MstUser_IDAkses` (`IDAkses`),
  CONSTRAINT `FK_MstUser_MstAkses`
    FOREIGN KEY (`IDAkses`) REFERENCES `MstAkses` (`ID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =====================================================
-- PRODUCT MASTERS
-- =====================================================
CREATE TABLE `MstKategoriProduk` (
  `ID`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NamaProduk`    VARCHAR(50)  NOT NULL,  -- nama kategori
  `Deskripsi`     VARCHAR(255) NOT NULL,
  `Notes`         VARCHAR(255) NOT NULL,
  `CreatedAt`     DATETIME(6) NOT NULL,
  `CreatedBy`     BIGINT UNSIGNED NOT NULL,
  `ModifiedAt`    DATETIME(6) NULL,
  `ModifiedBy`    BIGINT UNSIGNED NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `MstProduk` (
  `ID`               BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `IDKategoriProduk` BIGINT UNSIGNED NOT NULL,
  `NamaProduk`       VARCHAR(50)  NOT NULL,
  `Merk`             VARCHAR(50)  NOT NULL,
  `Model`            VARCHAR(50)  NOT NULL,
  `Warna`            VARCHAR(30)  NOT NULL,
  `DeskripsiProduk`  VARCHAR(255) NOT NULL,
  `Stok`             INT NOT NULL,
  `CreatedAt`        DATETIME(6) NOT NULL,
  `CreatedBy`        BIGINT UNSIGNED NOT NULL,
  `ModifiedAt`       DATETIME(6) NULL,
  `ModifiedBy`       BIGINT UNSIGNED NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_MstProduk_IDKategoriProduk` (`IDKategoriProduk`),
  CONSTRAINT `FK_MstProduk_MstKategoriProduk`
    FOREIGN KEY (`IDKategoriProduk`) REFERENCES `MstKategoriProduk` (`ID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `CK_MstProduk_Stok_NonNegative`
    CHECK (`Stok` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `MstSupplier` (
  `ID`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NamaSupplier`   VARCHAR(50)  NOT NULL,
  `AlamatSupplier` VARCHAR(255) NOT NULL,
  `CreatedAt`      DATETIME(6) NOT NULL,
  `CreatedBy`      BIGINT UNSIGNED NOT NULL,
  `ModifiedAt`     DATETIME(6) NULL,
  `ModifiedBy`     BIGINT UNSIGNED NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `MapProdukSupplier` (
  `IDProduk`   BIGINT UNSIGNED NOT NULL,
  `IDSupplier` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`IDProduk`, `IDSupplier`),
  KEY `IX_MapProdukSupplier_IDSupplier` (`IDSupplier`),
  CONSTRAINT `FK_MapProdukSupplier_MstProduk`
    FOREIGN KEY (`IDProduk`)   REFERENCES `MstProduk` (`ID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_MapProdukSupplier_MstSupplier`
    FOREIGN KEY (`IDSupplier`) REFERENCES `MstSupplier` (`ID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =====================================================
-- LOGGING TABLES
-- =====================================================
CREATE TABLE `LogProduk` (
  `ID`               BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `IDProduk`         BIGINT UNSIGNED NULL,
  `IDKategoriProduk` BIGINT UNSIGNED NOT NULL,
  `NamaProduk`       VARCHAR(50)  NOT NULL,
  `Merk`             VARCHAR(50)  NOT NULL,
  `Model`            VARCHAR(50)  NOT NULL,
  `Warna`            VARCHAR(30)  NOT NULL,
  `DeskripsiProduk`  VARCHAR(255) NOT NULL,
  `Stok`             INT NOT NULL,
  `Flag`             CHAR(1) NOT NULL,  -- I/U/D
  `CreatedAt`        DATETIME(6) NOT NULL,
  `CreatedBy`        BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_LogProduk_ProdukWaktu` (`IDProduk`, `CreatedAt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `LogKategoriProduk` (
  `ID`               BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `IDKategoriProduk` BIGINT UNSIGNED NULL,
  `NamaProduk`       VARCHAR(50)  NOT NULL, -- nama kategori
  `Deskripsi`        VARCHAR(255) NOT NULL,
  `Notes`            VARCHAR(255) NOT NULL,
  `Flag`             CHAR(1) NOT NULL,
  `CreatedAt`        DATETIME(6) NOT NULL,
  `CreatedBy`        BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_LogKategoriProduk_Waktu` (`IDKategoriProduk`, `CreatedAt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `LogSupplier` (
  `ID`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `IDSupplier`     BIGINT UNSIGNED NULL,
  `NamaSupplier`   VARCHAR(50)  NOT NULL,
  `AlamatSupplier` VARCHAR(255) NOT NULL,
  `Flag`           CHAR(1) NOT NULL,
  `CreatedAt`      DATETIME(6) NOT NULL,
  `CreatedBy`      BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_LogSupplier_Waktu` (`IDSupplier`, `CreatedAt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

---

## 4) Catatan Implementasi (MySQL Tips)
- **Timestamps Otomatis** (opsional): 
  - Tambahkan `DEFAULT CURRENT_TIMESTAMP(6)` dan/atau `ON UPDATE CURRENT_TIMESTAMP(6)` pada kolom `CreatedAt/ModifiedAt` sesuai alur aplikasi agar konsisten.
- **Password**: simpan hash (mis. Argon2/BCrypt) dengan panjang kolom memadai; `VARCHAR(64)` hanya placeholder.
- **Kebijakan FK**: ganti ke `ON DELETE CASCADE` bila ingin otomatis menghapus mapping saat master dihapus (mis. pada `MapAksesMenu`, `MapProdukSupplier`).
- **Partisi / Arsitektur Log**: untuk volume besar pada tabel log, pertimbangkan partisi per tanggal dan index composite `(FK, CreatedAt)` sudah disiapkan.
- **Boolean**: gunakan `TINYINT(1)`; interpretasi sebagai true/false dikelola aplikasi.

---

_Selesai._
