package com.aashi.saas.dto;

import java.time.LocalDateTime;

public class ProjectMemberResponseDto {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String roleInProject;
    private LocalDateTime joinedAt;

    public ProjectMemberResponseDto(Long id, Long userId, String username, String email,
                                     String roleInProject, LocalDateTime joinedAt) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roleInProject = roleInProject;
        this.joinedAt = joinedAt;
    }

    // getters only (response object)
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRoleInProject() { return roleInProject; }
    public LocalDateTime getJoinedAt() { return joinedAt; }
}
