package com.aashi.saas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aashi.saas.dto.TenantRequestDTO;
import com.aashi.saas.dto.TenantResponseDTO;
import com.aashi.saas.entity.Tenant;
import com.aashi.saas.exception.TenantAlreadyFoundException;
import com.aashi.saas.exception.TenantNotFoundException;
import com.aashi.saas.repository.TenantRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TenantService {
	
	
	private final TenantRepository tenantRepository;

   
    public TenantResponseDTO createTenant(TenantRequestDTO request) {
        if (tenantRepository.existsByName(request.getName())) {
            throw new TenantAlreadyFoundException("Tenant with name " + request.getName() + " already exists.");
        }
        Tenant tenant = new Tenant();
        tenant.setName(request.getName());
        Tenant saved = tenantRepository.save(tenant);
        return new TenantResponseDTO(saved.getId(), saved.getName());
    }

  
    public TenantResponseDTO getTenantById(long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant with id " + id + " not found."));
        return new TenantResponseDTO(tenant.getId(), tenant.getName());
    }

    
    public List<TenantResponseDTO> getAllTenants() {
        return tenantRepository.findAll().stream()
                .map(t -> new TenantResponseDTO(t.getId(), t.getName()))
                .toList();
    }

  
    public TenantResponseDTO updateTenant(long id, TenantRequestDTO request) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant with id " + id + " not found."));

        if (!tenant.getName().equals(request.getName())
                && tenantRepository.existsByName(request.getName())) {
            throw new TenantAlreadyFoundException("Tenant with name " + request.getName() + " already exists.");
        }

        tenant.setName(request.getName());
        Tenant updated = tenantRepository.save(tenant);
        return new TenantResponseDTO(updated.getId(), updated.getName());
    }

 
    public void deleteTenant(long id) {
        if (!tenantRepository.existsById(id)) {
            throw new TenantNotFoundException("Tenant with id " + id + " not found.");
        }
        tenantRepository.deleteById(id);
    }

}
