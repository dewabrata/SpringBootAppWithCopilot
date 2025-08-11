-- =====================================================
-- ERDJBE26 Database Schema & Initial Data
-- DBMS: MySQL 8.0
-- =====================================================



-- Use the created database
USE `erdjbe26_db`;

-- =====================================================
-- Session Defaults (opsional)
-- =====================================================
SET SESSION sql_mode = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- =====================================================
-- Drop Existing Tables (in reverse order of dependency)
-- =====================================================
DROP TABLE IF EXISTS `log_supplier`;
DROP TABLE IF EXISTS `log_kategori_produk`;
DROP TABLE IF EXISTS `log_produk`;
DROP TABLE IF EXISTS `map_produk_supplier`;
DROP TABLE IF EXISTS `mst_supplier`;
DROP TABLE IF EXISTS `mst_produk`;
DROP TABLE IF EXISTS `mst_kategori_produk`;
DROP TABLE IF EXISTS `map_akses_menu`;
DROP TABLE IF EXISTS `mst_user`;
DROP TABLE IF EXISTS `mst_menu`;
DROP TABLE IF EXISTS `mst_group_menu`;
DROP TABLE IF EXISTS `mst_akses`;


-- =====================================================
-- MASTER & SECURITY
-- =====================================================
CREATE TABLE `mst_akses` (
  `ID`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Nama`          VARCHAR(50)  NOT NULL,
  `Deskripsi`     VARCHAR(255) NOT NULL,
  `CreatedBy`     BIGINT UNSIGNED NOT NULL,
  `CreatedDate`   DATETIME(6) NULL,
  `ModifiedBy`    BIGINT UNSIGNED NULL,
  `ModifiedDate`  DATETIME(6) NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_MstAkses_Nama` (`Nama`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `mst_group_menu` (
  `ID`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Nama`          VARCHAR(50)  NOT NULL,
  `Deskripsi`     VARCHAR(255) NOT NULL,
  `CreatedBy`     BIGINT UNSIGNED NOT NULL,
  `CreatedDate`   DATETIME(6) NULL,
  `ModifiedBy`    BIGINT UNSIGNED NULL,
  `ModifiedDate`  DATETIME(6) NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `mst_menu` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `mst_user` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `map_akses_menu` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- PRODUCT MASTERS
-- =====================================================
CREATE TABLE `mst_kategori_produk` (
  `ID`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NamaProduk`    VARCHAR(50)  NOT NULL,  -- nama kategori
  `Deskripsi`     VARCHAR(255) NOT NULL,
  `Notes`         VARCHAR(255) NOT NULL,
  `CreatedAt`     DATETIME(6) NOT NULL,
  `CreatedBy`     BIGINT UNSIGNED NOT NULL,
  `ModifiedAt`    DATETIME(6) NULL,
  `ModifiedBy`    BIGINT UNSIGNED NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `mst_produk` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `mst_supplier` (
  `ID`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NamaSupplier`   VARCHAR(50)  NOT NULL,
  `AlamatSupplier` VARCHAR(255) NOT NULL,
  `CreatedAt`      DATETIME(6) NOT NULL,
  `CreatedBy`      BIGINT UNSIGNED NOT NULL,
  `ModifiedAt`     DATETIME(6) NULL,
  `ModifiedBy`     BIGINT UNSIGNED NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `map_produk_supplier` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- LOGGING TABLES
-- =====================================================
CREATE TABLE `log_produk` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `log_kategori_produk` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `log_supplier` (
  `ID`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `IDSupplier`     BIGINT UNSIGNED NULL,
  `NamaSupplier`   VARCHAR(50)  NOT NULL,
  `AlamatSupplier` VARCHAR(255) NOT NULL,
  `Flag`           CHAR(1) NOT NULL,
  `CreatedAt`      DATETIME(6) NOT NULL,
  `CreatedBy`      BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_LogSupplier_Waktu` (`IDSupplier`, `CreatedAt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- =====================================================
-- INITIAL DATA (SEEDING)
-- =====================================================

-- 1. Seed MstAkses (Roles)
-- User ID 1 is considered the 'SYSTEM' user for initial data
INSERT INTO `mst_akses` (`ID`, `Nama`, `Deskripsi`, `CreatedBy`, `CreatedDate`) VALUES
(1, 'ADMIN', 'Administrator, full access', 1, NOW(6)),
(2, 'STAFF', 'Staff, can manage products and suppliers', 1, NOW(6)),
(3, 'VIEWER', 'Viewer, read-only access', 1, NOW(6));

-- 2. Seed MstUser (Admin User)
-- Password is 'password', hashed with BCrypt
INSERT INTO `mst_user` (`ID`, `Username`, `Password`, `NamaLengkap`, `Email`, `NoHp`, `Alamat`, `TanggalLahir`, `IsRegistered`, `IDAkses`, `CreatedBy`, `CreatedDate`) VALUES
(1, 'admin', '$2a$10$GRLdNijSQeR/u.22FYowdu2C2k.uD.3e.iZq.8L.wz.3Zz.4C.S/S', 'Administrator', 'admin@erdjbe26.com', '081234567890', 'System Address', '2000-01-01', 1, 1, 1, NOW(6));

-- 3. Seed MstGroupMenu
INSERT INTO `mst_group_menu` (`ID`, `Nama`, `Deskripsi`, `CreatedBy`, `CreatedDate`) VALUES
(1, 'Main', 'Main Navigation', 1, NOW(6)),
(2, 'Settings', 'Application Settings', 1, NOW(6)),
(3, 'Masters', 'Master Data Management', 1, NOW(6)),
(4, 'Logs', 'Audit Trail Logs', 1, NOW(6));

-- 4. Seed MstMenu
INSERT INTO `mst_menu` (`ID`, `Nama`, `Path`, `Deskripsi`, `IDGroupMenu`, `CreatedBy`, `CreatedDate`) VALUES
(1, 'Dashboard', '/dashboard', 'Main dashboard page', 1, 1, NOW(6)),
(2, 'Users', '/users', 'User Management', 2, 1, NOW(6)),
(3, 'Roles', '/roles', 'Role and Access Management', 2, 1, NOW(6)),
(4, 'Menus', '/menus', 'Menu Management', 2, 1, NOW(6)),
(5, 'Categories', '/categories', 'Product Category Management', 3, 1, NOW(6)),
(6, 'Products', '/products', 'Product Management', 3, 1, NOW(6)),
(7, 'Suppliers', '/suppliers', 'Supplier Management', 3, 1, NOW(6)),
(8, 'Product Logs', '/logs/products', 'Product Audit Trail', 4, 1, NOW(6)),
(9, 'Category Logs', '/logs/categories', 'Category Audit Trail', 4, 1, NOW(6)),
(10, 'Supplier Logs', '/logs/suppliers', 'Supplier Audit Trail', 4, 1, NOW(6));

-- 5. Seed MapAksesMenu (Give ADMIN access to all menus)
INSERT INTO `MapAksesMenu` (`IDAkses`, `IDMenu`) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10);

-- Give STAFF access to a subset of menus
INSERT INTO `MapAksesMenu` (`IDAkses`, `IDMenu`) VALUES
(2, 1), (2, 5), (2, 6), (2, 7);

-- Give VIEWER read-only access (can see dashboard and lists)
INSERT INTO `MapAksesMenu` (`IDAkses`, `IDMenu`) VALUES
(3, 1), (3, 5), (3, 6), (3, 7);

-- =====================================================
-- End of Script
-- =====================================================
