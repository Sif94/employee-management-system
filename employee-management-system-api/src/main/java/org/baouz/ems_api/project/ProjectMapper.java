package org.baouz.ems_api.project;

import org.springframework.stereotype.Service;

@Service
public class ProjectMapper {


    public Project toProject(ProjectRequest request) {
        return Project.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .status(request.status())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .tags(request.tags())
                .build();
    }

    public ProjectResponse toProjectResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .tags(project.getTags())
                .departmentId(project.getDepartment().getId())
                .build();
    }
}
