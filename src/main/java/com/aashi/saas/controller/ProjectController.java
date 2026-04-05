package com.aashi.saas.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aashi.saas.entity.Project;
import com.aashi.saas.service.ProjectService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@SecurityRequirement(name="bearerAuth")
public class ProjectController {
	
	private final ProjectService projectService;

	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Page<Project> getAll(@ParameterObject Pageable pageable)
	{
		return projectService.getAllProject(pageable);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Project createProject(@RequestBody Project project)
	{
		return projectService.createProject(project);
	}

}
