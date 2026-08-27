package com.aashi.saas.dto;


public class ProjectMemberRequestDto {
    private Long userId;
    private String roleInProject;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getRoleInProject() { return roleInProject; }
    public void setRoleInProject(String roleInProject) { this.roleInProject = roleInProject; }
}