package com.aashi.saas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashi.saas.entity.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long>{

}
