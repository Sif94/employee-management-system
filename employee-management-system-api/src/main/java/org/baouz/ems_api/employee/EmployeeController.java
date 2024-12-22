package org.baouz.ems_api.employee;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baouz.ems_api.common.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
@Tag(name = "Employee")
public class EmployeeController {


    private final EmployeeService service;
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'HR')")
    public ResponseEntity<String> saveEmployee(
            @RequestBody @Valid EmployeeRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(request));
    }
    @PostMapping(value = "/picture/{employee-id}", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'HR')")
    public ResponseEntity<?> uploadProfilePicture(
            @PathVariable("employee-id") String employeeId,
            @Parameter()
            @RequestPart("file") MultipartFile file
    ) {
        service.uploadProfilePicture(file, employeeId);
        return ResponseEntity.accepted().build();
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN', 'HR')")
    public ResponseEntity<PageResponse<EmployeeResponse>> findAllEmployees(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ){
        return ResponseEntity.ok(service.findAll(page,size));
    }

    @GetMapping("/{employee-id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'HR')")
    public ResponseEntity<EmployeeResponse> findEmployeeById(@PathVariable("employee-id") String employeeId) {
        return ResponseEntity.ok(service.findById(employeeId));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'HR')")
    public ResponseEntity<String> updateEmployee(
            @RequestBody @Valid EmployeeRequest request
    ){
        return ResponseEntity.ok(service.updateEmployee(request));
    }
    @DeleteMapping("{employee-id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<?> archiveEmployee(@PathVariable("employee-id") String employeeId) {
        service.archiveEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/department/{department-id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'HR')")
    public ResponseEntity<PageResponse<EmployeeResponse>> findEmployeeByDepartmentId(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @PathVariable("department-id") String departmentId
    ){
        return ResponseEntity.ok(service.findEmployeesByDepartmentId(page,size,departmentId));
    }
}
