package com.aashi.saas.controller;

import com.aashi.saas.dto.ProjectMemberRequestDto;
import com.aashi.saas.dto.ProjectMemberResponseDto;
import com.aashi.saas.service.ProjectMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @PostMapping
    public ResponseEntity<ProjectMemberResponseDto> addMember(
            @PathVariable Long projectId,
            @RequestBody ProjectMemberRequestDto request) {
        ProjectMemberResponseDto response = projectMemberService.addMember(projectId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectMemberResponseDto>> getMembers(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectMemberService.getMembers(projectId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long projectId,
            @PathVariable Long userId) {
        projectMemberService.removeMember(projectId, userId);
        return ResponseEntity.noContent().build();
    }
}
