package com.aashi.saas.service;

import com.aashi.saas.context.TenantContext;
import com.aashi.saas.dto.ProjectMemberRequestDto;
import com.aashi.saas.dto.ProjectMemberResponseDto;
import com.aashi.saas.entity.Project;
import com.aashi.saas.entity.ProjectMember;
import com.aashi.saas.entity.Tenant;
import com.aashi.saas.entity.User;
import com.aashi.saas.exception.ProjectMemberAlreadyExistsException;
import com.aashi.saas.exception.ProjectMemberNotFoundException;
import com.aashi.saas.exception.ProjectNotFoundException;
import com.aashi.saas.repository.ProjectMemberRepository;
import com.aashi.saas.repository.ProjectRepository;
import com.aashi.saas.repository.TenantRepository;
import com.aashi.saas.repository.UserRepository;
import com.aashi.saas.service.filter.TenantFilterService;
import com.aashi.saas.utility.UtilityClass;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectMemberService extends TenantFilterService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final AuditLogService auditLogService;

    public ProjectMemberService(ProjectMemberRepository projectMemberRepository,
                                 ProjectRepository projectRepository,
                                 UserRepository userRepository,
                                 TenantRepository tenantRepository,
                                 AuditLogService auditLogService) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.auditLogService = auditLogService;
    }

    public ProjectMemberResponseDto addMember(Long projectId, ProjectMemberRequestDto request) {
        enableTenantFilter();
        Long currentTenantId = TenantContext.getTenantId();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found: " + projectId));
        validateTenantOwnership(project, currentTenantId);
        validateProjectAdmin(project);

        User userToAdd = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getUserId()));

        // same-tenant check for the user being added
        if (!(userToAdd.getTenant().getId()==currentTenantId)) {
            throw new RuntimeException("User does not belong to your tenant");
        }

        if (projectMemberRepository.existsByProjectIdAndUserId(projectId, request.getUserId())) {
            throw new ProjectMemberAlreadyExistsException("User is already a member of the project");
        }

        Tenant tenant = tenantRepository.findById(currentTenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        ProjectMember member = new ProjectMember();
        member.setProject(project);
        member.setUser(userToAdd);
        member.setTenant(tenant);
        member.setRoleInProject(request.getRoleInProject());

        ProjectMember saved = projectMemberRepository.save(member);

        String actingUsername = UtilityClass.getCurrentUser().getUsername();
        auditLogService.logAction("Add Project Member", actingUsername, "PROJECT_MEMBER", saved.getId());

        return toDto(saved);
    }

    public List<ProjectMemberResponseDto> getMembers(Long projectId) {
        enableTenantFilter();
        Long currentTenantId = TenantContext.getTenantId();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found: " + projectId));
        validateTenantOwnership(project, currentTenantId);

        return projectMemberRepository.findByProjectId(projectId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void removeMember(Long projectId, Long userId) {
        enableTenantFilter();
        Long currentTenantId = TenantContext.getTenantId();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found: " + projectId));
        validateTenantOwnership(project, currentTenantId);
        validateProjectAdmin(project);

        ProjectMember member = projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ProjectMemberNotFoundException("Project member not found for user: " + userId));

        projectMemberRepository.delete(member);

        String actingUsername = UtilityClass.getCurrentUser().getUsername();
        auditLogService.logAction("Remove Project Member", actingUsername, "PROJECT_MEMBER", member.getId());
    }

    // ---- helpers ----

    private void validateTenantOwnership(Project project, Long currentTenantId) {
        if (!(project.getTenant().getId()==currentTenantId)) {
            throw new ProjectNotFoundException("Project does not belong to your tenant");
        }
    }

    private void validateProjectAdmin(Project project) {
        Long currentUserId = UtilityClass.getCurrentUser().getUserId();
        if (!(project.getAdmin().getId()==currentUserId)) {
            throw new AccessDeniedException("Only the project admin can manage members");
        }
    }

    private ProjectMemberResponseDto toDto(ProjectMember member) {
        return new ProjectMemberResponseDto(
                member.getId(),
                member.getUser().getId(),
                member.getUser().getUsername(),
                member.getUser().getEmail(),
                member.getRoleInProject(),
                member.getJoinedAt()
        );
    }
}
