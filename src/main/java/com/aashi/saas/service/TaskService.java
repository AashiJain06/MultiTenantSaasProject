package com.aashi.saas.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.dto.CreateTaskDto;
import com.aashi.saas.dto.ResponseTaskDto;

import com.aashi.saas.entity.Project;
import com.aashi.saas.entity.Task;
import com.aashi.saas.entity.TaskStatus;
import com.aashi.saas.entity.Tenant;
import com.aashi.saas.entity.User;
import com.aashi.saas.exception.TenantNotFoundException;
import com.aashi.saas.exception.UserNotFoundException;

import com.aashi.saas.repository.ProjectRepository;
import com.aashi.saas.repository.TaskRepository;
import com.aashi.saas.repository.TenantRepository;
import com.aashi.saas.repository.UserRepository;
import com.aashi.saas.security.CustomUserDetails;
import com.aashi.saas.service.filter.TenantFilterService;
import com.aashi.saas.utility.UtilityClass;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService extends TenantFilterService{
	
	private final TenantRepository tenantRepository;
	private final TaskRepository taskRepository;
	private final UserRepository userRepository;
	private final ProjectRepository projectRepository;
	private final AuditLogService auditLogService;
	private final AIService aiService;
	
	public Page<ResponseTaskDto> getAllTaskOfProject(Long projectId,Pageable pageable)
	{
		
		enableTenantFilter();
		Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Project Not Found"));
		validateProjectAdmin(project);
		return taskRepository.findByProjectId(projectId,pageable)
 				.map(task -> new ResponseTaskDto(task.getId(),task.getTitle(),task.getDiscription(),task.getStatus(),task.getAssignedUser().getId(),task.getProject().getId()
 						));

	}
	
	
	public ResponseTaskDto createTask(CreateTaskDto taskDto) {
		enableTenantFilter();
		 Project project = projectRepository.findById(taskDto.getProjectId()).orElseThrow(()->new RuntimeException("Project not found"));
	        validateProjectAdmin(project);
		Long tenantId = TenantContext.getTenantId();
        
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found"));
        User user = userRepository.findById(taskDto.getUserId()).orElseThrow(()->new UserNotFoundException("User not found"));
        
        if(user.getTenant().getId()!=TenantContext.getTenantId()) {
            throw new RuntimeException("User does not belong to current tenant");
        }
        
        if (project.getTenant().getId() != TenantContext.getTenantId()) {
            throw new RuntimeException("Project does not belong to current tenant");
        }
        
        if (user.getTenant().getId() != project.getTenant().getId()) {
            throw new RuntimeException("User and Project belong to different tenants");
        }
            Task task = new Task();
            task.setTenant(tenant);
            task.setAssignedUser(user);
            task.setTitle(taskDto.getTitle());
            task.setProject(project);
            task.setDiscription(taskDto.getDescription());
            task.setStatus(TaskStatus.TODO);
        	task =  taskRepository.save(task);
        	CustomUserDetails currentUser = UtilityClass.getCurrentUser();
    	    String username = currentUser.getUsername();
        	auditLogService.logAction("Create Task", username, "TASK", task.getId());
        	return new ResponseTaskDto(task.getId(),task.getTitle(),task.getDiscription(),task.getStatus(),task.getAssignedUser().getId(),task.getProject().getId());
       }
	
	
     public Page<ResponseTaskDto> getTasksByProject(Long projectId,Pageable pageable)
     {
    	 enableTenantFilter();
    	 Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Project Not Found"));
    	 if(project.getTenant().getId()!=TenantContext.getTenantId())
    	 {
    		 throw new RuntimeException("Project does not belong to Tenant");
    	 }
    	 return  taskRepository.findByProjectId(projectId,pageable)
    			 .map(task -> new ResponseTaskDto(task.getId(),task.getTitle(),task.getDiscription(),task.getStatus(),task.getAssignedUser().getId(),task.getProject().getId()
    					   ));	 
    	 
     }
     
     
     public Page<ResponseTaskDto> getTasksByUser(Long userId,Pageable pageable,Long projectId)
     {
    	 enableTenantFilter();
    	 User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not Found"));
    	 if(user.getTenant().getId()!=TenantContext.getTenantId())
    	 {
    		 throw new RuntimeException("User does not belong to Tenant");
    	 }
    	 Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Project not found"));
    	 validateProjectAdmin(project);
    	 return taskRepository.findByProjectIdAndAssignedUser_Id(projectId,userId,pageable)
   .map(task -> new ResponseTaskDto(task.getId(),task.getTitle(),task.getDiscription(),task.getStatus(),task.getAssignedUser().getId(),task.getProject().getId()
		   ));
  				
     }
     
     
	 public ResponseTaskDto updateStatus(Long taskId, TaskStatus status) {
		Task task = taskRepository.findById(taskId).orElseThrow(()->new RuntimeException("Task Not Found"));
		task.setStatus(status);
		task =  taskRepository.save(task);
		
	    CustomUserDetails currentUser = UtilityClass.getCurrentUser();
	    String username = currentUser.getUsername();
    	auditLogService.logAction("Update Task Statusaa", username, "TASK", task.getId());
		return new ResponseTaskDto(task.getId(),task.getTitle(),task.getDiscription(),task.getStatus(),task.getAssignedUser().getId(),task.getProject().getId());
	 }
	 
	 
	 public Page<ResponseTaskDto> searchTasks(Long projectId, String keyword, Pageable pageable) {

		    enableTenantFilter();

		    Project project = projectRepository.findById(projectId)
		            .orElseThrow(() -> new RuntimeException("Project not found"));

		    validateProjectAdmin(project); 

		    return taskRepository
		            .findByProjectIdAndTitleContaining(projectId, keyword, pageable)
		            .map(task -> new ResponseTaskDto(task.getId(),task.getTitle(),task.getDiscription(),task.getStatus(),task.getAssignedUser().getId(),task.getProject().getId()
		         		   ));
		}
	 
	 public Page<ResponseTaskDto> getTaskByStatus(Long projectId , TaskStatus status, Pageable pageable)
	 {
		 enableTenantFilter();
		 Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("project not found"));
		  validateProjectAdmin(project);
		  return taskRepository.findByProjectIdAndStatus(projectId, status, pageable)
				  .map(task -> new ResponseTaskDto(task.getId(),task.getTitle(),task.getDiscription(),task.getStatus(),task.getAssignedUser().getId(),task.getProject().getId()
		         		   ));
	 }
	 
	 private void validateProjectAdmin(Project project) 
	 {
		   
            CustomUserDetails currentUser = UtilityClass.getCurrentUser();
		    if(project.getAdmin().getId() != currentUser.getUserId()) {
		        throw new RuntimeException("Only project admin allowed");
	 }
		}
	 
	 public String getAiTaskSummary() {

		    enableTenantFilter();

		    CustomUserDetails currentUser = UtilityClass.getCurrentUser();

		    List<Task> tasks = taskRepository.findByAssignedUserId(currentUser.getUserId());

		    if (tasks.isEmpty()) {
		        return "No tasks assigned to you";
		    }

		    String prompt = buildPrompt(tasks);

		    return aiService.getAiSummary(prompt);
		}
	 
	 private String buildPrompt(List<Task> tasks) {

	        StringBuilder sb = new StringBuilder();

	        sb.append("""
	        Summarize the following tasks.
	        Also include:
	        - total tasks
	        - completed tasks
	        - pending tasks
	        - suggest priority work

	        Tasks:
	        """);

	        for (Task task : tasks) {
	            sb.append("- ")
	              .append(task.getTitle())
	              .append(" (Status: ")
	              .append(task.getStatus())
	              .append(")\n");
	        }
			return sb.toString();
	 

  }
}
