package org.baouz.ems_api.employee;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baouz.ems_api.common.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
@Tag(name = "Employee")
public class EmployeeController {


    private final EmployeeService service;
    @PostMapping
    public ResponseEntity<String> saveEmployee(
            @RequestBody @Valid EmployeeRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(request));
    }
    @PostMapping(value = "/picture/{employee-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadProfilePicture(
            @PathVariable("employee-id") String employeeId,
            @Parameter()
            @RequestPart("file") MultipartFile file
    ) {
        service.uploadProfilePicture(file, employeeId);
        return ResponseEntity.accepted().build();
    }
    @GetMapping
    public ResponseEntity<PageResponse<EmployeeResponse>> findAllEmployees(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ){
        return ResponseEntity.ok(service.findAll(page,size));
    }
}