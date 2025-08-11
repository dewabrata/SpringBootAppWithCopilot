package com.erdjbe26.webservice.controller;

import com.erdjbe26.webservice.dto.AksesDto;
import com.erdjbe26.webservice.service.AksesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class AksesController {

    private final AksesService aksesService;

    public AksesController(AksesService aksesService) {
        this.aksesService = aksesService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AksesDto> createAkses(@Valid @RequestBody AksesDto aksesDto) {
        return new ResponseEntity<>(aksesService.createAkses(aksesDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AksesDto> getAksesById(@PathVariable Long id) {
        return ResponseEntity.ok(aksesService.getAksesById(id));
    }

    @GetMapping
    public ResponseEntity<List<AksesDto>> getAllAkses() {
        return ResponseEntity.ok(aksesService.getAllAkses());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AksesDto> updateAkses(@PathVariable Long id, @Valid @RequestBody AksesDto aksesDto) {
        return ResponseEntity.ok(aksesService.updateAkses(id, aksesDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAkses(@PathVariable Long id) {
        aksesService.deleteAkses(id);
        return ResponseEntity.noContent().build();
    }
}
