-- =====================================================
-- ERDJBE26 Initial Data Seeding
-- DBMS: MySQL 8.0
-- =====================================================

-- This script contains the essential data needed to run the application,
-- including the admin user, roles, and menu structure.

-- Use the target database
USE `erdjbe26_db`;

-- Disable foreign key checks to avoid order issues during insertion/truncation
SET FOREIGN_KEY_CHECKS=0;

-- Truncate tables to ensure a clean slate before inserting initial data
TRUNCATE TABLE `map_akses_menu`;
TRUNCATE TABLE `mst_user`;
TRUNCATE TABLE `mst_menu`;
TRUNCATE TABLE `mst_group_menu`;
TRUNCATE TABLE `mst_akses`;
-- Note: Product-related tables are not truncated as they are not essential for initial startup.

SET FOREIGN_KEY_CHECKS=0;
-- =====================================================
-- 1. Seed mst_akses (Roles)
-- =====================================================
INSERT INTO `mst_akses` (`id`, `nama`, `deskripsi`, `created_by`, `created_date`) VALUES
(1, 'ADMIN', 'Administrator, full access', 1, NOW(6)),
(2, 'STAFF', 'Staff, can manage products and suppliers', 1, NOW(6)),
(3, 'VIEWER', 'Viewer, read-only access', 1, NOW(6));


-- =====================================================
-- 2. Seed mst_user (Admin User)
-- Password is 'password', hashed with BCrypt
-- =====================================================
INSERT INTO `mst_user` (`id`, `username`, `password`, `nama_lengkap`, `email`, `no_hp`, `alamat`, `tanggal_lahir`, `is_registered`, `idakses`, `created_by`, `created_date`) VALUES
(1, 'admin', '$2a$10$GRLdNijSQeR/u.22FYowdu2C2k.uD.3e.iZq.8L.wz.3Zz.4C.S/S', 'Administrator', 'admin@erdjbe26.com', '081234567890', 'System Address', '2000-01-01', 1, 1, 1, NOW(6));


-- =====================================================
-- 3. Seed mst_group_menu
-- =====================================================
INSERT INTO `mst_group_menu` (`id`, `nama`, `deskripsi`, `created_by`, `created_date`) VALUES
(1, 'Main', 'Main Navigation', 1, NOW(6)),
(2, 'Settings', 'Application Settings', 1, NOW(6)),
(3, 'Masters', 'Master Data Management', 1, NOW(6)),
(4, 'Logs', 'Audit Trail Logs', 1, NOW(6));


-- =====================================================
-- 4. Seed mst_menu
-- =====================================================
INSERT INTO `mst_menu` (`id`, `nama`, `path`, `deskripsi`, `idgroup_menu`, `created_by`, `created_date`) VALUES
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


-- =====================================================
-- 5. Seed map_akses_menu (Role-Menu Mappings)
-- =====================================================
-- Give ADMIN access to all menus
INSERT INTO `map_akses_menu` (`idakses`, `idmenu`) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10);

-- Give STAFF access to a subset of menus
INSERT INTO `map_akses_menu` (`idakses`, `idmenu`) VALUES
(2, 1), (2, 5), (2, 6), (2, 7);

-- Give VIEWER read-only access (can see dashboard and lists)
INSERT INTO `map_akses_menu` (`idakses`, `idmenu`) VALUES
(3, 1), (3, 5), (3, 6), (3, 7);


-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS=1;

-- =====================================================
-- End of Script
-- =====================================================
