package com.aashi.saas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aashi.saas.entity.Task;
import com.aashi.saas.service.TaskService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
@SecurityRequirement(name="bearerAuth")
public class TaskController {
	
	private final TaskService taskService;
	
	@GetMapping
	public List<Task> getAll()
	{
		return taskService.getAlltask();
	}
	@PostMapping
	public Task create(@RequestBody Task task)
	{
		return taskService.createTask(task);
	}

}
