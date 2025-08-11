package com.erdjbe26.webservice.controller;

import com.erdjbe26.webservice.dto.ProdukDto;
import com.erdjbe26.webservice.service.ProdukService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProdukController {

    private final ProdukService produkService;

    public ProdukController(ProdukService produkService) {
        this.produkService = produkService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    public ResponseEntity<ProdukDto> createProduk(@Valid @RequestBody ProdukDto produkDto) {
        return new ResponseEntity<>(produkService.createProduk(produkDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdukDto> getProdukById(@PathVariable Long id) {
        return ResponseEntity.ok(produkService.getProdukById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProdukDto>> getAllProduk() {
        return ResponseEntity.ok(produkService.getAllProduk());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    public ResponseEntity<ProdukDto> updateProduk(@PathVariable Long id, @Valid @RequestBody ProdukDto produkDto) {
        return ResponseEntity.ok(produkService.updateProduk(id, produkDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    public ResponseEntity<Void> deleteProduk(@PathVariable Long id) {
        produkService.deleteProduk(id);
        return ResponseEntity.noContent().build();
    }
}
