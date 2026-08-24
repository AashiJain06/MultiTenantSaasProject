package com.aashi.saas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashi.saas.entity.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long>{
    
	Optional<Tenant> findByName(String name);
   
	boolean existsByName(String name);


}
