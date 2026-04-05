package com.aashi.saas.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aashi.saas.dto.CreateTaskDto;
import com.aashi.saas.dto.ResponseTaskDto;
import com.aashi.saas.entity.TaskStatus;
import com.aashi.saas.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
@Tag(name = "Task APIs", description = "APIS for mananging tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

	private final TaskService taskService;

	@Operation(summary = "Get tasks by Project", description = "Fetch all tasks for a specific project with Pagination and Sorting")
	@ApiResponse(responseCode = "200", description = "Tasks fetched successfully")
	@ApiResponse(responseCode = "403", description = "Access denied")
	@ApiResponse(responseCode = "404", description = "Project not found")
	@GetMapping("/project/{projectId}/get")
	@PreAuthorize("hasRole('ADMIN')")
	public Page<ResponseTaskDto> getAll(
			@Parameter(description = "Project ID whose task need to be fetched") @PathVariable Long projectId,
			@Parameter(description = "Pagination details like page, sort, size") @ParameterObject Pageable pageable) {

		return taskService.getAllTaskOfProject(projectId, pageable);
	}

	@Operation(summary = "create taks", description = "Create Task and assigned task to the User")
	@ApiResponse(responseCode = "200", description = "Tasks fetched successfully")
	@ApiResponse(responseCode = "403", description = "Access denied")
	@ApiResponse(responseCode = "404", description = "Project not found")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseTaskDto create(@Valid @RequestBody CreateTaskDto taskDto) {
		return taskService.createTask(taskDto);
	}

//	@Operation(
//			summary = "Get tasks by Project",
//			description = "Fetch all tasks for a specific project with Pagination and Sorting"
//				)
//
//	@ApiResponse(responseCode = "200", description = "Tasks fetched successfully")
//	@ApiResponse(responseCode = "403", description = "Access denied")
//	@ApiResponse(responseCode = "404", description = "Project not found")
//	@GetMapping("/project/{projectId}")
//	@PreAuthorize("hasRole('ADMIN')")
//	public Page<ResponseTaskDto> getByProject(@PathVariable Long projectId,Pageable pageable) {
//	    return taskService.getTasksByProject(projectId,pageable);
//	}

	@Operation(summary = "Get tasks by Assigned User", description = "Fetch all tasks of Assigned to the User with Pagination and Sorting")
	@ApiResponse(responseCode = "200", description = "Tasks fetched successfully")
	@ApiResponse(responseCode = "403", description = "Access denied")
	@ApiResponse(responseCode = "404", description = "Project or User not found")
	@GetMapping("/project/{projectId}/user/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public Page<ResponseTaskDto> getByUser(@PathVariable Long userId, @ParameterObject Pageable pageable,
			@PathVariable Long projectId) {
		return taskService.getTasksByUser(userId, pageable, projectId);
	}

	@Operation(
			summary = "Update Your task Status")			
	@PutMapping("/{taskId}/status")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseTaskDto updateStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) 
	{
	    return taskService.updateStatus(taskId, status);
	}

	
	@Operation(
			summary = "Search Task",
			description = "Search tasks by keyword within a project"
				)
	@GetMapping("/projects/{projectId}/tasks/search")
	public Page<ResponseTaskDto> searchTasks(
	        @PathVariable Long projectId,
	        @RequestParam String keyword, @ParameterObject Pageable pageable) 
	{

	    return taskService.searchTasks(projectId, keyword, pageable);
	}


}
