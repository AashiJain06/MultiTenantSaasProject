package com.aashi.saas.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.entity.Project;
import com.aashi.saas.entity.Tenant;
import com.aashi.saas.repository.ProjectRepository;
import com.aashi.saas.repository.TenantRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
   private final ProjectRepository projectRepository;
   private final TenantRepository tenantRepository;
   private final EntityManager entityManager;
   
   public List<Project> getAllProject()
   {
	   Session session = entityManager.unwrap(Session.class);
	   session.enableFilter("tenantFilter")
	   .setParameter("tenantId",TenantContext.getTenantId());
	   
	   return projectRepository.findAll();
	}
   
   public Project createProject(Project project)
   {
	   Long tenantId = TenantContext.getTenantId();
	   Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(()-> new RuntimeException("Tenant not Found"));
	   
	   project.setTenant(tenant);
	   return projectRepository.save(project);
   }
   
}
