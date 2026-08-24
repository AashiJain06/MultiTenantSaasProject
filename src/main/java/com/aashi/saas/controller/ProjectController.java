package com.aashi.saas.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aashi.saas.dto.ProjectRequestDto;
import com.aashi.saas.entity.Project;
import com.aashi.saas.service.ProjectService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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
	public Project createProject(@RequestBody ProjectRequestDto project)
	{
		return projectService.createProject(project);
	}
	
	@PutMapping("/{projectId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Project> updateProject(
	        @PathVariable Long projectId,
	        @Valid @RequestBody ProjectRequestDto projectDto) {
	    return ResponseEntity.ok(projectService.updateProject(projectId, projectDto));
	}

	@DeleteMapping("/{projectId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
	    projectService.deleteProject(projectId);
	    return ResponseEntity.noContent().build();
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<Project> getProject(@PathVariable Long projectId) {
	    return ResponseEntity.ok(projectService.getProjectById(projectId));
	}

}
