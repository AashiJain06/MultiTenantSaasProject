package com.aashi.saas.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
@SecurityRequirement(name="bearerAuth")
public class TaskController {
	
	private final TaskService taskService;
	
	@GetMapping
	public Page<ResponseTaskDto> getAll(Pageable pageable)
	{
		
		return taskService.getAlltask(pageable);
	}
	@PostMapping
	public ResponseTaskDto create(@Valid @RequestBody CreateTaskDto taskDto)
	{
		return taskService.createTask(taskDto);
	}
	@GetMapping("/project/{projectId}")
	public Page<ResponseTaskDto> getByProject(@PathVariable Long projectId,Pageable pageable) {
	    return taskService.getTasksByProject(projectId,pageable);
	}
	@GetMapping("/user/{userId}")
	public Page<ResponseTaskDto> getByUser(@PathVariable Long userId, Pageable pageable) {
	    return taskService.getTasksByUser(userId,pageable);
	}
	@PutMapping("/{taskId}/status")
	public ResponseTaskDto updateStatus(@PathVariable Long taskId,
	                         @RequestParam TaskStatus status) {
	    return taskService.updateStatus(taskId, status);
	}

}
