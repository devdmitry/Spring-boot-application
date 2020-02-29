package com.rohov.internal.controller.facade.mapper;

import com.rohov.internal.controller.facade.dto.ProjectDto;
import com.rohov.internal.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectDto toDto(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdDate(project.getCreatedDate())
                .updatedDate(project.getUpdatedDate())
                .build();
    }

    public Project toEntity(ProjectDto projectDto) {
        Project project = new Project();

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());

        return project;
    }

}
