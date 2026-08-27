package com.aashi.saas.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.stereotype.Service;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.dto.ProjectRequestDto;
import com.aashi.saas.entity.Project;
import com.aashi.saas.entity.Tenant;
import com.aashi.saas.entity.User;
import com.aashi.saas.exception.ProjectNotFoundException;
import com.aashi.saas.repository.ProjectRepository;
import com.aashi.saas.repository.TenantRepository;
import com.aashi.saas.repository.UserRepository;
import com.aashi.saas.security.CustomUserDetails;
import com.aashi.saas.service.filter.TenantFilterService;
import com.aashi.saas.utility.UtilityClass;


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
   public Project getProjectById(Long projectId) {
	    enableTenantFilter();
	    Project project = projectRepository.findById(projectId)
	            .orElseThrow(() -> new ProjectNotFoundException("Project with ID " + projectId + " not found"));

	    validateTenantAccess(project);
	    return project;
	}

	public Project updateProject(Long projectId, ProjectRequestDto projectDto) {
	    enableTenantFilter();
	    Project project = projectRepository.findById(projectId)
	            .orElseThrow(() -> new ProjectNotFoundException("Project with ID " + projectId + " not found"));

	    validateTenantAccess(project);
	    validateProjectAdmin(project);

	    project.setName(projectDto.getName());
	    project.setDescription(projectDto.getDescription());
	    projectRepository.save(project);

	    String username = UtilityClass.getCurrentUser().getUsername();
	    auditLogService.logAction("Update Project", username, "PROJECT", project.getId());
	    return project;
	}

	public void deleteProject(Long projectId) {
	    enableTenantFilter();
	    Project project = projectRepository.findById(projectId)
	            .orElseThrow(() -> new ProjectNotFoundException("Project with ID " + projectId + " not found"));

	    validateTenantAccess(project);
	    validateProjectAdmin(project);

	    String username = UtilityClass.getCurrentUser().getUsername();
	    projectRepository.delete(project);
	    auditLogService.logAction("Delete Project", username, "PROJECT", projectId);
	}

	// ---- helper methods ----

	private void validateTenantAccess(Project project) {
	    Long currentTenantId = TenantContext.getTenantId();
	    if (!(project.getTenant().getId()==currentTenantId)) {
	        throw new ProjectNotFoundException("Project with ID " + project.getId() + " not found in the current tenant");
	        // Not AccessDenied jaanbujh kar — tenant boundary ke bahar ka project
	        // "exist hi nahi karta" dikhna chahiye, "access denied" nahi.
	        // Warna attacker ko pata chal jayega ki ID valid hai bas access nahi.
	    }
	}

	private void validateProjectAdmin(Project project) {
	    Long currentUserId = UtilityClass.getCurrentUser().getUserId();
	    if (!(project.getAdmin().getId()==currentUserId)) {
	        throw new AccessDeniedException("Only the project admin can perform this action");
	    }
	}
}
   