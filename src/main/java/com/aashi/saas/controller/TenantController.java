package com.aashi.saas.controller;

import org.springframework.web.bind.annotation.RestController;

import com.aashi.saas.dto.TenantRequestDTO;
import com.aashi.saas.dto.TenantResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final com.aashi.saas.service.TenantService tenantService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantResponseDTO> createTenant(@Valid @RequestBody TenantRequestDTO request) {
        TenantResponseDTO response = tenantService.createTenant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantResponseDTO> getTenant(@PathVariable long id) {
        return ResponseEntity.ok(tenantService.getTenantById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<TenantResponseDTO>> getAllTenants() {
        return ResponseEntity.ok(tenantService.getAllTenants());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantResponseDTO> updateTenant(
            @PathVariable long id, @Valid @RequestBody TenantRequestDTO request) {
        return ResponseEntity.ok(tenantService.updateTenant(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteTenant(@PathVariable long id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }
}
