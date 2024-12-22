package org.baouz.ems_api.project;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baouz.ems_api.common.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("projects")
@RequiredArgsConstructor
@Tag(name = "Project")
public class ProjectController {

    private final ProjectService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<String> saveProject(
            @RequestBody @Valid ProjectRequest request
    ){
        return ResponseEntity
                .status(CREATED)
                .body(service.save(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<PageResponse<ProjectResponse>> findAllProjects(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ){
        return ResponseEntity.ok(service.findAll(page,size));
    }
    @GetMapping("/{project-id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<ProjectResponse> findProjectById(
            @PathVariable("project-id") String projectId
    ){
        return ResponseEntity.ok(service.findById(projectId));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<String> updateProject(
            @RequestBody @Valid ProjectRequest request
    ){
        return ResponseEntity.ok(service.updateProject(request));
    }

    @DeleteMapping("/{project-id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<?> archiveProject(
            @PathVariable("project-id") String projectId
    ){
        service.archiveProject(projectId);
        return ResponseEntity.noContent().build();
    }
}
