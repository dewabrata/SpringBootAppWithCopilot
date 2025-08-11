package com.erdjbe26.webservice.controller;

import com.erdjbe26.webservice.dto.LogKategoriProdukDto;
import com.erdjbe26.webservice.dto.LogProdukDto;
import com.erdjbe26.webservice.dto.LogSupplierDto;
import com.erdjbe26.webservice.entity.LogKategoriProduk;
import com.erdjbe26.webservice.entity.LogProduk;
import com.erdjbe26.webservice.entity.LogSupplier;
import com.erdjbe26.webservice.mapper.LogKategoriProdukMapper;
import com.erdjbe26.webservice.mapper.LogProdukMapper;
import com.erdjbe26.webservice.mapper.LogSupplierMapper;
import com.erdjbe26.webservice.repository.LogKategoriProdukRepository;
import com.erdjbe26.webservice.repository.LogProdukRepository;
import com.erdjbe26.webservice.repository.LogSupplierRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/logs")
@Tag(name = "Log Management", description = "APIs for retrieving audit logs")
public class LogController {

    private final LogProdukRepository logProdukRepository;
    private final LogKategoriProdukRepository logKategoriProdukRepository;
    private final LogSupplierRepository logSupplierRepository;
    private final LogProdukMapper logProdukMapper;
    private final LogKategoriProdukMapper logKategoriProdukMapper;
    private final LogSupplierMapper logSupplierMapper;

    public LogController(LogProdukRepository logProdukRepository,
                        LogKategoriProdukRepository logKategoriProdukRepository,
                        LogSupplierRepository logSupplierRepository,
                        LogProdukMapper logProdukMapper,
                        LogKategoriProdukMapper logKategoriProdukMapper,
                        LogSupplierMapper logSupplierMapper) {
        this.logProdukRepository = logProdukRepository;
        this.logKategoriProdukRepository = logKategoriProdukRepository;
        this.logSupplierRepository = logSupplierRepository;
        this.logProdukMapper = logProdukMapper;
        this.logKategoriProdukMapper = logKategoriProdukMapper;
        this.logSupplierMapper = logSupplierMapper;
    }

    @GetMapping("/produk")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get product audit logs", description = "Retrieve audit logs for products with optional filtering")
    public ResponseEntity<Page<LogProdukDto>> getLogProduk(
            @Parameter(description = "Filter by product ID") @RequestParam(required = false) Long idProduk,
            @Parameter(description = "Filter by start date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") 
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Filter by end date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") 
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<LogProduk> logPage = logProdukRepository.findLogProdukWithFilters(
            idProduk, startDate, endDate, pageable);
        
        Page<LogProdukDto> dtoPage = logPage.map(logProdukMapper::toDto);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/kategori-produk")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get category audit logs", description = "Retrieve audit logs for product categories with optional filtering")
    public ResponseEntity<Page<LogKategoriProdukDto>> getLogKategoriProduk(
            @Parameter(description = "Filter by category ID") @RequestParam(required = false) Long idKategoriProduk,
            @Parameter(description = "Filter by start date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") 
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Filter by end date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") 
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<LogKategoriProduk> logPage = logKategoriProdukRepository.findLogKategoriProdukWithFilters(
            idKategoriProduk, startDate, endDate, pageable);
        
        Page<LogKategoriProdukDto> dtoPage = logPage.map(logKategoriProdukMapper::toDto);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/supplier")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get supplier audit logs", description = "Retrieve audit logs for suppliers with optional filtering")
    public ResponseEntity<Page<LogSupplierDto>> getLogSupplier(
            @Parameter(description = "Filter by supplier ID") @RequestParam(required = false) Long idSupplier,
            @Parameter(description = "Filter by start date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") 
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Filter by end date (ISO format: yyyy-MM-dd'T'HH:mm:ss)") 
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<LogSupplier> logPage = logSupplierRepository.findLogSupplierWithFilters(
            idSupplier, startDate, endDate, pageable);
        
        Page<LogSupplierDto> dtoPage = logPage.map(logSupplierMapper::toDto);
        return ResponseEntity.ok(dtoPage);
    }
}