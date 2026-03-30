package com.aashi.saas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashi.saas.entity.AuditLog;

public  interface AuditLogRepository extends JpaRepository<AuditLog, Long>{

}
