package com.aashi.saas.service;

import org.springframework.stereotype.Service;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.dto.DashBoardDto;
import com.aashi.saas.dto.ProjectDashBoardDto;
import com.aashi.saas.entity.Project;
import com.aashi.saas.entity.TaskStatus;
import com.aashi.saas.repository.ProjectRepository;
import com.aashi.saas.repository.TaskRepository;
import com.aashi.saas.service.filter.TenantFilterService;
import com.aashi.saas.utility.UtilityClass;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DashBoardService extends TenantFilterService{

	private final ProjectRepository projectRepository;
	
	private final TaskRepository taskRepository;
	
	public DashBoardDto getDashboard()
	{
		enableTenantFilter();
		Long totalProject = projectRepository.count();
		Long totalTasks = taskRepository.count();
		
		Long completedTasks = taskRepository.countByStatus(TaskStatus.DONE);
		
		return new DashBoardDto(totalProject,totalTasks,completedTasks);
	}
	public ProjectDashBoardDto getProjectDashboard(Long projectId)
	{
		enableTenantFilter();
		Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Project Not Found"));
		if(project.getTenant().getId()!=TenantContext.getTenantId())
		{
			throw new RuntimeException("Project does not belong to tenant ");
		}
		Long totalTasks = taskRepository.countByProjectId(projectId);
		Long completed = taskRepository.countByProjectIdAndStatus(
	            projectId, TaskStatus.DONE);

	    Long todo = taskRepository.countByProjectIdAndStatus(
	            projectId, TaskStatus.TODO);

	    Long inProgress = taskRepository.countByProjectIdAndStatus(
	            projectId, TaskStatus.IN_PROGRESS);
		return new ProjectDashBoardDto(project.getId(),totalTasks,completed,todo,inProgress);
	}
	
}
