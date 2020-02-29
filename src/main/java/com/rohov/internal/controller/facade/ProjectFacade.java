package com.rohov.internal.controller.facade;

import com.rohov.internal.controller.facade.dto.CompanyDto;
import com.rohov.internal.controller.facade.dto.ProjectDto;
import com.rohov.internal.controller.facade.dto.RoleDto;
import com.rohov.internal.controller.facade.dto.UserDto;
import com.rohov.internal.entity.Project;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectFacade {

    List<ProjectDto> findAll(Long companyId, String name, Pageable pageable);

    CompanyDto findCompanyByProject(Long projectId);

    ProjectDto find(Long companyId, Long projectId);

    ProjectDto create(Long companyId, ProjectDto projectDto);

    ProjectDto update(Long companyId, Long projectId, ProjectDto projectDto);

    ProjectDto patch(Long companyId, Long projectId, ProjectDto projectDto);

    void delete(Long companyId, Long projectId);

    void addUserProjectPermission(Long companyId, Long projectId, Long userId, List<RoleDto> permissions);

    void changeUserProjectPermission(Long companyId, Long projectId, Long userId, List<RoleDto> permissions);

}
