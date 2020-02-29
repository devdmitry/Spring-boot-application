package com.rohov.internal.controller.facade.impl;


import com.rohov.internal.controller.facade.ProjectFacade;
import com.rohov.internal.controller.facade.dto.CompanyDto;
import com.rohov.internal.controller.facade.dto.ProjectDto;
import com.rohov.internal.controller.facade.dto.RoleDto;
import com.rohov.internal.controller.facade.mapper.CompanyMapper;
import com.rohov.internal.controller.facade.mapper.PermissionMapper;
import com.rohov.internal.controller.facade.mapper.ProjectMapper;
import com.rohov.internal.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ProjectFacadeImpl implements ProjectFacade {

    ProjectService projectService;

    CompanyMapper companyMapper;
    ProjectMapper projectMapper;
    PermissionMapper permissionMapper;

    @Override
    public List<ProjectDto> findAll(Long companyId, String name, Pageable pageable) {
        return projectService.findAll(companyId, name, pageable).stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto findCompanyByProject(Long id) {
        return companyMapper.toDto(projectService.findCompanyByProject(id));
    }

    @Override
    public ProjectDto find(Long companyId, Long id) {
        return projectMapper.toDto(projectService.findById(companyId, id));
    }

    @Override
    public ProjectDto create(Long companyId, ProjectDto projectDto) {
        return projectMapper.toDto(projectService.create(companyId, projectMapper.toEntity(projectDto)));
    }

    @Override
    public ProjectDto update(Long companyId, Long id, ProjectDto projectDto) {
        return projectMapper.toDto(projectService.update(companyId, id, projectMapper.toEntity(projectDto)));
    }

    @Override
    public ProjectDto patch(Long companyId, Long id, ProjectDto projectDto) {
        return projectMapper.toDto(projectService.patch(companyId, id, projectMapper.toEntity(projectDto)));
    }

    @Override
    public void delete(Long companyId, Long id) {
        projectService.delete(companyId, id);
    }

    @Override
    public void addUserProjectPermission(Long companyId, Long projectId, Long userId, List<RoleDto> permissions) {
        projectService.addUserProjectPermission(companyId, projectId, userId,
                permissions.stream()
                        .map(permissionMapper::toEntity)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void changeUserProjectPermission(Long companyId, Long projectId, Long userId, List<RoleDto> permissions) {
        projectService.changeUserProjectPermission(companyId, projectId, userId,
                permissions.stream()
                        .map(permissionMapper::toEntity)
                        .collect(Collectors.toList())
        );
    }
}
