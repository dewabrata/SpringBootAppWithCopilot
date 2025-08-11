
# Project_Brief — ERDJBE26
_WebService Spring Boot + ReactJS (Bootstrap)_

**Version:** 1.0 · **Target DB:** MySQL 8.0 (utf8mb4) · **Modules:** Access Control & Menu, User Management, Product Catalog, Supplier, Audit Logs

---

## 1) Executive Summary
Tujuan proyek ini adalah membangun **WebService** berbasis **Spring Boot** dengan **RESTful API** untuk mengelola akses/menu, pengguna, produk, kategori, supplier, serta **audit logging**. UI web dibuat dengan **ReactJS + Bootstrap**. Sistem memfasilitasi **role-based access control (RBAC)** dengan pemetaan **role ↔ menu**, manajemen katalog produk dan stok, pemasok, dan riwayat perubahan (log).

**Sasaran utama:**
- API aman, versioned (`/api/v1`), terdokumentasi (OpenAPI/Swagger).
- UI responsif berbasis Bootstrap, terintegrasi dengan RBAC (menu dinamis).
- Audit trail komprehensif untuk master (produk, kategori, supplier).
- Dapat di-deploy on-prem / cloud (Docker-ready).

---

## 2) Scope
**In-scope**  
1. Autentikasi & RBAC: login, session/JWT, otorisasi berbasis `MstAkses`, `MstMenu`, `MapAksesMenu`.
2. Manajemen User: CRUD, assign role (`IDAkses`), profile, reset password (OTP optional).
3. Master Data: `MstKategoriProduk`, `MstProduk`, `MstSupplier`.
4. Mapping: `MapProdukSupplier` (N..N).
5. Audit Log: `LogProduk`, `LogKategoriProduk`, `LogSupplier` via service layer.
6. UI dasar: Dashboard, Users, Roles, Menus, Categories, Products, Suppliers, Logs.
7. Utilitas: Upload link/path image di user & product (opsional, disimpan sebagai string).

**Out-of-scope (fase ini):**
- Payment, purchase order, inventory movement yang kompleks (transfer gudang, dsb.).
- Multi-tenant, SSO, SAML/OIDC enterprise. (Bisa jadi fase 2).

---

## 3) Architecture Overview
- **Backend:** Spring Boot 3.x, Spring Web, Spring Security, Spring Data JPA (Hibernate), Validation, MapStruct (DTO), Springdoc OpenAPI, Lombok.
- **Database:** MySQL 8.0 (InnoDB, utf8mb4_0900_ai_ci), Flyway/Liquibase untuk migration.
- **Frontend:** React 18, React Router, React Query (atau Redux Toolkit Query), Axios/Fetch, Bootstrap 5, React-Bootstrap, Formik + Yup.
- **Build/CI:** Maven, Vite/CRA, Docker, GitHub Actions (CI/CD).
- **Packaging:** Docker Compose (api + mysql + web).

### High-Level Diagram
```
React (Bootstrap) → REST API /api/v1 → Spring Boot Service → JPA → MySQL
                                   ↘︎ Audit Logger → Log* tables
                                   ↘︎ Security (JWT/RBAC)
```

---

## 4) Database Entities (ringkas)
- **Security & Menu**: `MstAkses`, `MstGroupMenu`, `MstMenu`, `MapAksesMenu`, `MstUser`
- **Catalog**: `MstKategoriProduk`, `MstProduk`, `MstSupplier`, `MapProdukSupplier`
- **Logs**: `LogProduk`, `LogKategoriProduk`, `LogSupplier`

Referensi struktur kolom & tipe: lihat _database_mysql.md_.

---

## 5) Business Processes

### 5.1 Autentikasi & RBAC
1. **Login** (username/email + password). Backend verifikasi hash (BCrypt/Argon2).
2. **Issue JWT** berisi `userId`, `roleId`, expiry. Refresh token opsional.
3. **Authorize** per endpoint via anotasi security dan/atau policy berbasis **menu path**:
   - Setelah login, frontend memanggil `/me/menus` → daftar menu dari `MapAksesMenu(IDAkses, IDMenu)`.
   - UI menampilkan **sidebar**/navbar dinamis sesuai menu tersebut.
4. **Session Management**: token di HTTP-only cookie atau memory + CSRF strategy bila pakai cookie.

### 5.2 Manajemen Role & Menu
- Admin CRUD **MstAkses** & **MstMenu**, susun **MapAksesMenu**.
- Perubahan menu langsung mempengaruhi navigasi UI pengguna dengan role terkait.

### 5.3 Manajemen User
- Admin membuat user baru, set `IDAkses` dan status `IsRegistered`.
- User mengubah profil: `NamaLengkap`, `NoHp`, `Alamat`, `LinkImage/PathImage`.
- **Reset Password**: kirim OTP (kolom `OTP`) via email/SMS gateway (opsional fase 1), lalu set password baru.

### 5.4 Manajemen Kategori & Produk
- CRUD `MstKategoriProduk` (nama kategori, deskripsi, notes).
- CRUD `MstProduk`: wajib pilih `IDKategoriProduk`, isi atribut (merk, model, warna, deskripsi, stok).
- **Business Rule**: `Stok ≥ 0`. Update stok melalui form edit produk atau aksi khusus (opsional endpoint `/stock-adjust`).

### 5.5 Manajemen Supplier & Mapping Produk–Supplier
- CRUD `MstSupplier`.
- Kelola **MapProdukSupplier** dari detail product (tab "Suppliers"): tambah/hapus supplier terkait.

### 5.6 Audit Logging
- Setiap **insert/update/delete** pada master:
  - Simpan snapshot ke `Log*` dengan `Flag` = `I`/`U`/`D`, `CreatedAt`, `CreatedBy` (id user).
- **Usee-case**: pelacakan siapa mengubah stok/atribut produk dan kapan.

### 5.7 Pelaporan Sederhana (opsional)
- Daftar produk per kategori, daftar supplier per produk, riwayat perubahan produk dalam rentang tanggal.

---

## 6) API Design (v1)

> Base URL: `/api/v1` · Semua response JSON · Pagination: `page`, `size` · Sorting: `sort=field,asc|desc` · Filter: query params

### 6.1 Auth
- `POST /auth/login` → `{ usernameOrEmail, password }` → `{ accessToken, tokenType, expiresIn, user }`
- `POST /auth/logout` (opsional kalau pakai cookie)
- `GET /auth/me` → profil + role
- `GET /auth/me/menus` → daftar menu untuk user saat ini

### 6.2 Roles & Menus
- `GET /roles` / `POST /roles` / `PUT /roles/{id}` / `DELETE /roles/{id}`
- `GET /menus` / `POST /menus` / `PUT /menus/{id}` / `DELETE /menus/{id}`
- `GET /roles/{id}/menus` / `PUT /roles/{id}/menus` (terima array `menuIds`)

### 6.3 Users
- `GET /users` (q, roleId, registered) / `POST /users`
- `GET /users/{id}` / `PUT /users/{id}` / `DELETE /users/{id}`
- `PUT /users/{id}/password` (admin) / `POST /users/password/reset` (via OTP)
- `GET /users/check-username?value=...` / `GET /users/check-email?value=...`

### 6.4 Categories
- `GET /categories` / `POST /categories`
- `GET /categories/{id}` / `PUT /categories/{id}` / `DELETE /categories/{id}`

### 6.5 Products
- `GET /products` (q, categoryId, supplierId, minStock, maxStock) / `POST /products`
- `GET /products/{id}` / `PUT /products/{id}` / `DELETE /products/{id}`
- `PUT /products/{id}/stock-adjust` → `{ delta, reason }` (log otomatis)
- `GET /products/{id}/suppliers` / `PUT /products/{id}/suppliers` (array `supplierIds`)

### 6.6 Suppliers
- `GET /suppliers` / `POST /suppliers`
- `GET /suppliers/{id}` / `PUT /suppliers/{id}` / `DELETE /suppliers/{id}`

### 6.7 Logs
- `GET /logs/products` (productId, dateFrom, dateTo, flag, actorId)
- `GET /logs/categories` (categoryId, dateFrom, dateTo, flag, actorId)
- `GET /logs/suppliers` (supplierId, dateFrom, dateTo, flag, actorId)

**Response Example (paged):**
```json
{
  "content": [{ "id": 1, "namaProduk": "Kemeja", "stok": 20 }],
  "page": 0,
  "size": 10,
  "totalElements": 120,
  "totalPages": 12,
  "sort": "namaProduk,asc"
}
```

---

## 7) Backend Design

### 7.1 Layers
- **Controller** (`@RestController`) → **Service** (transaction, business rule, logging) → **Repository** (Spring Data JPA).
- **DTO/Mapper**: Request/Response DTO; MapStruct untuk konversi Entity↔DTO.
- **Validation**: `javax.validation` (`@NotNull`, `@Email`, custom validator stok ≥ 0).
- **Security**: Spring Security (JWT). `@PreAuthorize("hasAuthority('MENU_PRODUCTS')")` atau dynamic policy dari DB (Path menu).

### 7.2 Transactions & Audit
- Service method dibungkus `@Transactional`.  
- Interceptor/Aspect untuk tulis ke `Log*` setelah operasi sukses.

### 7.3 Migrations
- **Flyway**: `V1__baseline.sql` (DDL dari `database_mysql.md`), `V2__seed.sql` (seed minimal).

### 7.4 Error Handling
- Global exception handler → format error seragam `{timestamp, path, message, details, code}`.

---

## 8) Frontend Design (React + Bootstrap)

### 8.1 Layout & Navigasi
- **Topbar** + **Sidebar** (Bootstrap navbar + offcanvas) → menu dinamis dari `/auth/me/menus`.
- **Halaman utama**: Dashboard (kartu ringkas: total produk, kategori, supplier).
- **Entity pages**: Tabel (Bootstrap Table) + pencarian, filter, sort, pagination.
- **Forms**: Modal/halaman dengan Formik + Yup (validasi sinkron).

### 8.2 State & Data Fetching
- **React Query** untuk cache, refetch, stale-while-revalidate.
- Hooks khusus `useProducts`, `useCategories`, dsb. Abstraksi API via Axios instance (interceptor JWT).

### 8.3 Komponen Kunci
- `ProtectedRoute` (cek token + role).
- `MenuProvider` (context daftar menu).
- `DataTable` (generic table dengan kolom dinamis).
- `ConfirmDialog`, `Toast` (feedback UX).

### 8.4 RBAC di UI
- Tampilkan/hilangkan menu & tombol aksi berdasarkan daftar menu user (mis. `MENU_PRODUCTS_CREATE`).

---

## 9) Non-Functional Requirements
- **Security**: BCrypt password, JWT expiry ≤ 1h, CORS whitelist, input validation, rate limit (gateway/nginx), HTTPS.
- **Performance**: Index pada FK & kolom pencarian, pagination default 10–50, N+1 query guard (fetch join bila perlu).
- **Reliability**: Healthcheck `/actuator/health`, readiness/liveness untuk K8s (opsional).
- **Observability**: Access log, audit log, structured logging (JSON), traceId per request.
- **Internationalization**: label UI en/id (opsional fase 2).

---

## 10) Seed Data (minimal)
- **Akses/Role**: `ADMIN`, `STAFF`, `VIEWER`.
- **Menu**: `DASHBOARD`, `USERS`, `ROLES`, `MENUS`, `CATEGORIES`, `PRODUCTS`, `SUPPLIERS`, `LOGS` (+ path).
- **Mapping**: ADMIN → semua; STAFF → subset CRUD; VIEWER → read-only.
- **User admin**: `admin / <bcrypt-hash>`.

---

## 11) Delivery & DevOps
- **Environments**: `dev`, `staging`, `prod` (profile Spring).
- **Docker**: `docker-compose.yml` (mysql:8, api, web).
- **CI/CD**: Lint/test → build Docker → push registry → deploy.
- **Config**: Secrets (DB pass, JWT secret) via env vars.
- **Backups**: mysqldump harian untuk prod, rotasi 7/30 hari.

---

## 12) Testing Strategy
- **Backend**: Unit (JUnit5/Mockito), Integration (Testcontainers MySQL), Contract tests (OpenAPI).
- **Frontend**: Unit (Vitest/Jest, React Testing Library), E2E (Playwright/Cypress).
- **Security tests**: authz/authn, path traversal, mass assignment, SQLi (param binding).

---

## 13) Acceptance Criteria (ringkas)
1. Login menghasilkan JWT, menu dinamis sesuai role.
2. CRUD Users/Roles/Menus berfungsi; mapping role↔menu tersimpan & tercermin di UI.
3. CRUD Kategori/Produk/Supplier; stok tidak bisa < 0.
4. Mapping Produk↔Supplier bekerja (add/remove).
5. Semua perubahan master tercatat di tabel log dengan `Flag` & `CreatedBy/At`.
6. API terdokumentasi otomatis (Swagger UI) & lulus basic performance (p95 < 300ms untuk operasi simple pada dev dataset).
7. UI responsif, pagination & pencarian berfungsi.

---

## 14) Example Payloads

**Create Product**
```json
POST /api/v1/products
{
  "idKategoriProduk": 1,
  "namaProduk": "Kemeja Oxford",
  "merk": "Alpha",
  "model": "OX-2025",
  "warna": "Navy",
  "deskripsiProduk": "Kemeja lengan panjang",
  "stok": 50
}
```
**Adjust Stock**
```json
PUT /api/v1/products/123/stock-adjust
{
  "delta": -5,
  "reason": "Penjualan harian"
}
```
**Set Suppliers for Product**
```json
PUT /api/v1/products/123/suppliers
[2, 5, 7]
```

---

## 15) Risks & Mitigation
- **RBAC kompleks** → mulai dari menu berbasis path sederhana; evolusi ke policy granular per aksi.
- **Audit noise** → filter & pagination di halaman log; retensi & arsip periodik.
- **Password reset** → pastikan channel OTP aman; rate limit & expiry OTP.

---

## 16) Timeline (indikatif, 4–6 minggu)
1. Setup proyek, DB migrations, seed & auth dasar.
2. Modul Roles/Menus/Users + UI.
3. Modul Categories/Products/Suppliers + UI.
4. Audit log, pelaporan sederhana, polishing & tests.
5. Dockerization, CI/CD, hardening.

---

_Selesai._
