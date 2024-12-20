package org.baouz.ems_api.employee;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.baouz.ems_api.common.PageResponse;
import org.baouz.ems_api.department.Department;
import org.baouz.ems_api.department.DepartmentRepository;
import org.baouz.ems_api.file.FileStorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {
    private final EmployeeRepository repository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper mapper;
    private final FileStorageService fileStorageService;

    public String save(EmployeeRequest request) {
        var employee = mapper.toEmployee(request);
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Department with ID:: %s not found", request.departmentId()))
                );
        employee.setDepartment(department);
        return repository.save(employee).getId();
    }

    public PageResponse<EmployeeResponse> findAll(Integer page, Integer size) {
        Page<Employee> employeePage = repository.findAllByIsArchived(false,PageRequest.of(page, size));
        List<EmployeeResponse> list = employeePage
                .getContent()
                .stream()
                .map(mapper::toEmployeeResponse)
                .toList();
        return PageResponse.<EmployeeResponse>builder()
                .page(page)
                .size(size)
                .totalPages(employeePage.getTotalPages())
                .totalElements(employeePage.getTotalElements())
                .content(list)
                .isFirst(employeePage.isFirst())
                .isLast(employeePage.isLast())
                .build();
    }

    public void uploadProfilePicture(MultipartFile file, String employeeId) {
        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("No employee found with ID:: " + employeeId));
        var profilePicture = fileStorageService.saveFile(file, employeeId);
        employee.setPicture(profilePicture);
        repository.save(employee);
    }

    public EmployeeResponse findById(String employeeId) {
        return repository.findById(employeeId)
                .map(mapper::toEmployeeResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException("No employee found with ID:: " + employeeId)
                );
    }

    public String updateEmployee(EmployeeRequest request) {
        repository.findById(request.id())
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Employee with ID %s was not found", request.id()))
                );
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Department with ID:: %s not found", request.departmentId()))
                );
        var employee = mapper.toEmployee(request);
        employee.setDepartment(department);
        return repository.save(employee).getId();
    }

    public void archiveEmployee(String employeeId) {
        Employee employee = repository.findById(employeeId)
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Employee with ID %s was not found", employeeId))
                );
        employee.setIsArchived(true);
        repository.save(employee);
    }
}
