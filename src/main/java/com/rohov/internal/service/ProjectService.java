package com.rohov.internal.service;

import com.rohov.internal.entity.Company;
import com.rohov.internal.entity.Permission;
import com.rohov.internal.entity.Project;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {

    List<Project> findAll(Long companyId, String name, Pageable pageable);

    Project findById(Long projectId);

    Company findCompanyByProject(Long projectId);

    Project findById(Long companyId, Long id);

    Project create(Long companyId, Project project);

    Project update(Long companyId, Long id, Project project);

    Project patch(Long companyId, Long id, Project project);

    void delete(Long companyId, Long id);

    void addUserProjectPermission(Long companyId, Long projectId, Long userId, List<Permission> permissions);

    void changeUserProjectPermission(Long companyId, Long projectId, Long userId, List<Permission> permissions);
}
