package com.rohov.internal.service.impl;

import com.rohov.internal.entity.Company;
import com.rohov.internal.entity.Permission;
import com.rohov.internal.entity.Project;
import com.rohov.internal.entity.User;
import com.rohov.internal.entity.UserProjectPermission;
import com.rohov.internal.exception.BusinessException;
import com.rohov.internal.exception.ErrorMessages;
import com.rohov.internal.exception.NotFoundException;
import com.rohov.internal.repository.jpa.ProjectRepository;
import com.rohov.internal.repository.jpa.UserProjectPermissionRepository;
import com.rohov.internal.service.CompanyService;
import com.rohov.internal.service.PermissionService;
import com.rohov.internal.service.ProjectService;
import com.rohov.internal.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    CompanyService companyService;
    UserService userService;
    PermissionService permissionService;
    UserProjectPermissionRepository userProjectPermissionRepository;

    @Override
    public List<Project> findAll(Long companyId, String name, Pageable pageable) {
        if (Objects.isNull(name)) {
            return projectRepository.findAllByCompany_Id(companyId, pageable).getContent();
        }
        return projectRepository.findAllByCompany_IdAndNameContains(companyId, name, pageable).getContent();
    }

    @Override
    public Project findById(Long projectId) {
        final String errMsgId = String.format(ErrorMessages.PROJECT_WRONG_ID , projectId);
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(errMsgId));
    }

    @Override
    public Company findCompanyByProject(Long projectId) {
        return findById(projectId).getCompany();
    }

    @Override
    public Project findById(Long companyId, Long id) {
        final String errMsgId = String.format(ErrorMessages.PROJECT_WRONG_ID , id);
        final String errMsgCompany = String.format(ErrorMessages.PROJECT_NOT_OWNED_BY_COMPANY , companyId);

        Project currentProject = projectRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(errMsgId);
        });

        if (!currentProject.getCompany().getId().equals(companyId)) {
            throw new BusinessException(errMsgCompany);
        }
        return currentProject;
    }

    @Override
    public Project create(Long companyId, Project project) {
        Company company = companyService.find(companyId);

        project.setCompany(company);

        return projectRepository.save(project);
    }

    @Override
    public Project update(Long companyId, Long id, Project project) {
        Project currentProject = findById(companyId, id);

        currentProject.setName(project.getName());
        currentProject.setDescription(project.getDescription());
        currentProject.setUpdatedDate(Instant.now());

        return projectRepository.save(currentProject);
    }

    @Override
    public Project patch(Long companyId, Long id, Project project) {
        Project currentProject = findById(companyId, id);

        if (Objects.nonNull(project.getName())) {
            currentProject.setName(project.getName());
        }
        if (Objects.nonNull(project.getDescription())) {
            currentProject.setDescription(project.getDescription());
        }

        currentProject.setUpdatedDate(Instant.now());

        return projectRepository.save(currentProject);
    }

    @Override
    public void delete(Long companyId, Long id) {
        Project currentProject = findById(companyId, id);
        projectRepository.delete(currentProject);
    }

    @Transactional
    @Override
    public void addUserProjectPermission(Long companyId, Long projectId, Long userId, List<Permission> permissions) {
        Project project = findById(companyId, projectId);
        User user = userService.findById(userId);
        for (Permission permission : permissions) {
            permission = permissionService.findByName(permission.getName());

            UserProjectPermission userProjectPermission = new UserProjectPermission();
            userProjectPermission.setProject(project);
            userProjectPermission.setUser(user);
            userProjectPermission.setPermission(permission);

            userProjectPermissionRepository.save(userProjectPermission);
        }
    }

    @Override
    public void changeUserProjectPermission(Long companyId, Long projectId, Long userId, List<Permission> permissions) {

    }
}
