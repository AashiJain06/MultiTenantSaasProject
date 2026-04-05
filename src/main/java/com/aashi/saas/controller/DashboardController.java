package com.aashi.saas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aashi.saas.dto.DashBoardDto;
import com.aashi.saas.dto.ProjectDashBoardDto;
import com.aashi.saas.service.DashBoardService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@SecurityRequirement(name="bearerAuth")
@Tag(name="Dashboard API", description = "You can access the count of total projects and tasks currently running in the Tenant")
public class DashboardController {

	private final DashBoardService dashboardService;
	
	@GetMapping
	public DashBoardDto getDashboard()
	{
		return dashboardService.getDashboard();
	}
	@GetMapping("/projects/{projectId}")
	public ProjectDashBoardDto getprojectDashboard(@PathVariable Long projectId)
	{
		return dashboardService.getProjectDashboard(projectId);
	}
}
