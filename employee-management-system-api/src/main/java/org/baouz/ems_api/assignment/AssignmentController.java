package org.baouz.ems_api.assignment;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baouz.ems_api.common.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("assignments")
@Tag(name = "Assignment")
public class AssignmentController {
    private final AssignmentService service;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<String> saveAssignment(
            @RequestBody @Valid AssignmentRequest request,
            Authentication connectedUser
    ) throws MessagingException {
        return ResponseEntity.status(CREATED)
                .body(service.save(request, connectedUser));
    }

    @GetMapping("/{assignment-id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<AssignmentResponse> findAssignmentById(
            @PathVariable("assignment-id") String assignmentId
    ){
        return ResponseEntity.ok(service.findAssignmentById(assignmentId));
    }

    @GetMapping("/project/{project-id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<PageResponse<AssignmentResponse>> findAllAssignmentsByProjectId(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @PathVariable("project-id") String projectId
    ){
        return ResponseEntity.ok(service.findAllAssignmentsByProjectId(page,size, projectId));
    }
    @GetMapping("/employee/{employee-id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<PageResponse<AssignmentResponse>> findAllAssignmentsByEmployeeId(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @PathVariable("employee-id") String employeeId
    ){
        return ResponseEntity.ok(service.findAllAssignmentsByEmployeeId(page,size, employeeId));
    }
    @PutMapping
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<String> updateAssignment(
            @RequestBody @Valid AssignmentRequest request
    ) throws MessagingException {
        return ResponseEntity.ok(service.updateAssignment(request));
    }

    @DeleteMapping("/{assignment-id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<?> deleteAssignmentById(
            @PathVariable("assignment-id") String assignmentId
    ){
        service.deleteAssignmentById(assignmentId);
        return ResponseEntity.noContent().build();
    }
}
