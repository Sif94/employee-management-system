package org.baouz.ems_api.department;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("departments")
@RequiredArgsConstructor
@Tag(name = "Department")
public class DepartmentController {

    private final DepartmentService service;

    @PostMapping
    public ResponseEntity<String> saveDepartment(
            @RequestBody @Valid DepartmentRequest request
    ) {
        return ResponseEntity
                .status(CREATED)
                .body(service.save(request));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> findAllDepartments() {
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("{department-id}")
    public ResponseEntity<DepartmentResponse> findDepartmentById(
        @PathVariable("department-id") String departmentId
    ){
        return ResponseEntity.ok(service.findById(departmentId));
    }

    @PutMapping
    public ResponseEntity<String> updateDepartment(
            @RequestBody @Valid DepartmentRequest request
    ){
        return ResponseEntity.ok(service.updateDepartment(request));
    }

    @DeleteMapping("/{department-id}")
    public ResponseEntity<?> deleteDepartmentById(@PathVariable("department-id") String departmentId){
        service.deleteDepartmentById(departmentId);
        return ResponseEntity.noContent().build();
    }
}