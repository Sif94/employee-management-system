package org.baouz.ems_api.project;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.baouz.ems_api.common.PageResponse;
import org.baouz.ems_api.department.Department;
import org.baouz.ems_api.department.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;
    private final DepartmentRepository departmentRepository;
    private final ProjectMapper mapper;

    public String save(ProjectRequest request) {
        var project = mapper.toProject(request);
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Department with ID:: %s was not found", request.departmentId()))
                );
        project.setDepartment(department);
        return repository.save(project).getId();
    }

    public PageResponse<ProjectResponse> findAll(Integer page, Integer size) {
        var pageRequest = PageRequest.of(page, size);
        Page<Project> projectPage = repository.findAllByIsArchivedIsFalse(pageRequest);
        List<ProjectResponse> projectResponses = projectPage.getContent()
                .stream()
                .map(mapper::toProjectResponse)
                .toList();
        return PageResponse.<ProjectResponse>builder()
                .page(projectPage.getNumber())
                .size(projectPage.getSize())
                .totalElements(projectPage.getTotalElements())
                .totalPages(projectPage.getTotalPages())
                .content(projectResponses)
                .isFirst(projectPage.isFirst())
                .isLast(projectPage.isLast())
                .build();
    }

    public ProjectResponse findById(String projectId) {
        return repository.findById(projectId)
                .map(mapper::toProjectResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Project with ID:: %s was not found", projectId))
                );
    }

    public String updateProject(ProjectRequest request) {
        repository.findById(request.id())
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Project with ID:: %s was not found", request.id()))
                );
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Department with ID:: %s was not found", request.departmentId()))
                );
        var newProject = mapper.toProject(request);
        newProject.setDepartment(department);
        return repository.save(newProject).getId();
    }

    public void archiveProject(String projectId) {
        Project project = repository.findById(projectId)
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Project with ID:: %s was not found", projectId))
                );
        project.setArchived(true);
        repository.save(project);
    }
}
