# Log API Documentation

## Overview
The Log API provides endpoints to retrieve audit log data for various entities in the SpringBootAppWithCopilot application. The API supports pagination and filtering to efficiently retrieve log data.

## Authentication
All Log API endpoints require authentication with JWT token and ADMIN role authorization.

## Endpoints

### 1. Get Product Logs
**GET** `/api/v1/logs/produk`

Retrieves audit logs for products with optional filtering and pagination.

**Query Parameters:**
- `idProduk` (optional): Filter by specific product ID
- `startDate` (optional): Filter by start date (ISO format: yyyy-MM-dd'T'HH:mm:ss)
- `endDate` (optional): Filter by end date (ISO format: yyyy-MM-dd'T'HH:mm:ss)  
- `page` (optional, default: 0): Page number (0-based)
- `size` (optional, default: 20): Page size

**Example Request:**
```
GET /api/v1/logs/produk?idProduk=1&page=0&size=10
```

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "idProduk": 1,
      "idKategoriProduk": 1,
      "namaProduk": "Product Name",
      "merk": "Brand",
      "model": "Model",
      "warna": "Color",
      "deskripsiProduk": "Description",
      "stok": 100,
      "flag": "C",
      "createdAt": "2024-01-01T10:00:00",
      "createdBy": 1
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "empty": true,
      "unsorted": true
    },
    "pageNumber": 0,
    "pageSize": 20,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true,
  "numberOfElements": 1,
  "size": 20,
  "number": 0,
  "sort": {
    "sorted": false,
    "empty": true,
    "unsorted": true
  },
  "empty": false
}
```

### 2. Get Category Logs
**GET** `/api/v1/logs/kategori-produk`

Retrieves audit logs for product categories with optional filtering and pagination.

**Query Parameters:**
- `idKategoriProduk` (optional): Filter by specific category ID
- `startDate` (optional): Filter by start date (ISO format: yyyy-MM-dd'T'HH:mm:ss)
- `endDate` (optional): Filter by end date (ISO format: yyyy-MM-dd'T'HH:mm:ss)
- `page` (optional, default: 0): Page number (0-based)  
- `size` (optional, default: 20): Page size

**Example Request:**
```
GET /api/v1/logs/kategori-produk?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59
```

### 3. Get Supplier Logs
**GET** `/api/v1/logs/supplier`

Retrieves audit logs for suppliers with optional filtering and pagination.

**Query Parameters:**
- `idSupplier` (optional): Filter by specific supplier ID
- `startDate` (optional): Filter by start date (ISO format: yyyy-MM-dd'T'HH:mm:ss)
- `endDate` (optional): Filter by end date (ISO format: yyyy-MM-dd'T'HH:mm:ss)
- `page` (optional, default: 0): Page number (0-based)
- `size` (optional, default: 20): Page size

**Example Request:**
```
GET /api/v1/logs/supplier?idSupplier=1&page=0&size=5
```

## Log Flags
The `flag` field in log entries indicates the type of operation:
- `C`: Create operation
- `U`: Update operation  
- `D`: Delete operation

## Notes
- All logs are ordered by `createdAt` in descending order (newest first)
- The API uses Spring Data JPA pagination for efficient data retrieval
- Date filtering is inclusive of the specified dates
- All endpoints require JWT authentication with ADMIN role
- Swagger UI is available at `/swagger-ui.html` for interactive API documentation