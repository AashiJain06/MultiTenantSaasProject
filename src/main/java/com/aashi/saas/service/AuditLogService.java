package com.aashi.saas.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.entity.AuditLog;
import com.aashi.saas.repository.AuditLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditLogService {
   private final AuditLogRepository auditLogRepository;
   
   public void logAction(String action , String username, String entity,Long entityId)
   {
	   AuditLog log = AuditLog.builder()
               .action(action)
               .username(username)
               .entity(entity)
               .entityId(entityId)
               .timeStamp(LocalDateTime.now())
               .tenantId(TenantContext
            		   .getTenantId())
               .build();

       auditLogRepository.save(log);
   }
}
