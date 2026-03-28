package com.aashi.saas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.dto.CreateTaskDto;
import com.aashi.saas.dto.ResponseTaskDto;
import com.aashi.saas.dto.UserResponseDto;
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
import com.aashi.saas.service.filter.TenantFilterService;


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
	
	public List<Task> getAlltask()
	{
		enableTenantFilter();
		return taskRepository.findAll();
	}
	public ResponseTaskDto createTask(CreateTaskDto taskDto) {
		enableTenantFilter();
        
		Long tenantId = TenantContext.getTenantId();
        
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found"));
        
        User user = userRepository.findById(taskDto.getUserId()).orElseThrow(()->new UserNotFoundException("User not found"));
        
        Project project = projectRepository.findById(taskDto.getProjectId()).orElseThrow(()->new RuntimeException("Project not found"));
        
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
            task.setAssigned_user(user);
            task.setTitle(taskDto.getTitle());
            task.setProject(project);
            task.setDiscription(taskDto.getDescription());
            task.setStatus(TaskStatus.TODO);
        	task =  taskRepository.save(task);
        	return new ResponseTaskDto(task.getId(),task.getTitle(),task.getDiscription(),task.getStatus(),task.getAssigned_user().getId(),task.getProject().getId());
       }
     public List<ResponseTaskDto> getTasksByProject(Long projectId)
     {
    	 enableTenantFilter();
    	 List<Task> task =  taskRepository.findBYProjectId(projectId);
    	 return task.stream()
 				.map(task -> new ResponseTaskDto(task.getId(),task.getTitle(),task.getDiscription(),task.getStatus(),task.getAssigned_user().getId(),task.getProject().getId())
 				.toList();
     }
     public List<Task> getTasksByUser(Long userId)
     {
    	 enableTenantFilter();
    	 return taskRepository.findByAssigned_user(userId);
     }
	 public Task updateStatus(Long taskId, TaskStatus status) {
		Task task = taskRepository.findById(taskId).orElseThrow(()->new RuntimeException("Task Not Found"));
		task.setStatus(status);
		return taskRepository.save(task);
	 }

}
