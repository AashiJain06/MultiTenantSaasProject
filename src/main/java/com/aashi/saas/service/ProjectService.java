package com.aashi.saas.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.dto.ProjectRequestDto;
import com.aashi.saas.entity.Project;
import com.aashi.saas.entity.Tenant;
import com.aashi.saas.entity.User;
import com.aashi.saas.repository.ProjectRepository;
import com.aashi.saas.repository.TenantRepository;
import com.aashi.saas.repository.UserRepository;
import com.aashi.saas.security.CustomUserDetails;
import com.aashi.saas.service.filter.TenantFilterService;
import com.aashi.saas.utility.UtilityClass;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService extends TenantFilterService{
   private final ProjectRepository projectRepository;
   private final TenantRepository tenantRepository;
   private final AuditLogService auditLogService;
   private final UserRepository userRepository;
   
   public Page<Project> getAllProject(Pageable pageable)
   {
	  
	   enableTenantFilter();
	   return projectRepository.findAll(pageable);
	}
   
   public Project createProject(ProjectRequestDto projectDto)
   {
	   Long tenantId = TenantContext.getTenantId(); 
	   Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(()-> new RuntimeException("Tenant not Found"));
	   CustomUserDetails currentUser = UtilityClass.getCurrentUser();
	   Long userId = currentUser.getUserId();
	   User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User Not Found"));
       Project project = new Project();
       project.setName(projectDto.getName());
       project.setDescription(projectDto.getDescription());
       project.setTenant(tenant);
       project.setAdmin(user);
	    projectRepository.save(project);

   	String username = UtilityClass.getCurrentUser().getUsername();
   	auditLogService.logAction("Create Project", username, "PROJECT", project.getId());
   	return project;
   }
   
   
   
}
