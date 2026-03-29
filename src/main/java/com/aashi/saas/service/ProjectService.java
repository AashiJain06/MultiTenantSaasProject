package com.aashi.saas.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.entity.Project;
import com.aashi.saas.entity.Tenant;
import com.aashi.saas.repository.ProjectRepository;
import com.aashi.saas.repository.TenantRepository;
import com.aashi.saas.service.filter.TenantFilterService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService extends TenantFilterService{
   private final ProjectRepository projectRepository;
   private final TenantRepository tenantRepository;
 
   
   public Page<Project> getAllProject(Pageable pageable)
   {
	  
	   enableTenantFilter();
	   return projectRepository.findAll(pageable);
	}
   
   public Project createProject(Project project)
   {
	   Long tenantId = TenantContext.getTenantId();
	   Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(()-> new RuntimeException("Tenant not Found"));
	   
	   project.setTenant(tenant);
	   return projectRepository.save(project);
   }
   
}
