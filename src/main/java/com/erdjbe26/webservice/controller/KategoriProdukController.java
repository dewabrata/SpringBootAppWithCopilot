package com.erdjbe26.webservice.controller;

import com.erdjbe26.webservice.dto.KategoriProdukDto;
import com.erdjbe26.webservice.service.KategoriProdukService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class KategoriProdukController {

    private final KategoriProdukService kategoriProdukService;

    public KategoriProdukController(KategoriProdukService kategoriProdukService) {
        this.kategoriProdukService = kategoriProdukService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    public ResponseEntity<KategoriProdukDto> createKategoriProduk(@Valid @RequestBody KategoriProdukDto kategoriProdukDto) {
        return new ResponseEntity<>(kategoriProdukService.createKategoriProduk(kategoriProdukDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KategoriProdukDto> getKategoriProdukById(@PathVariable Long id) {
        return ResponseEntity.ok(kategoriProdukService.getKategoriProdukById(id));
    }

    @GetMapping
    public ResponseEntity<List<KategoriProdukDto>> getAllKategoriProduk() {
        return ResponseEntity.ok(kategoriProdukService.getAllKategoriProduk());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    public ResponseEntity<KategoriProdukDto> updateKategoriProduk(@PathVariable Long id, @Valid @RequestBody KategoriProdukDto kategoriProdukDto) {
        return ResponseEntity.ok(kategoriProdukService.updateKategoriProduk(id, kategoriProdukDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    public ResponseEntity<Void> deleteKategoriProduk(@PathVariable Long id) {
        kategoriProdukService.deleteKategoriProduk(id);
        return ResponseEntity.noContent().build();
    }
}
