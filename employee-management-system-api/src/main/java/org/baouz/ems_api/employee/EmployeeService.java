package org.baouz.ems_api.employee;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baouz.ems_api.common.PageResponse;
import org.baouz.ems_api.department.Department;
import org.baouz.ems_api.department.DepartmentRepository;
import org.baouz.ems_api.file.FileStorageService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
@Slf4j
public class EmployeeService {
    private final EmployeeRepository repository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper mapper;
    private final FileStorageService fileStorageService;



    @CacheEvict(value = "employees", allEntries = true)
    public String save(EmployeeRequest request) {
        var employee = mapper.toEmployee(request);
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Department with ID:: %s not found", request.departmentId()))
                );
        employee.setDepartment(department);
        return repository.save(employee).getId();
    }
    @Cacheable(value = "employees", key = "#page + '-' + #size")
    public PageResponse<EmployeeResponse> findAll(Integer page, Integer size) {
        log.info("Find all employees from DB");
        Page<Employee> employeePage = repository.findAllByIsArchivedIsFalse(PageRequest.of(page, size));
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

    @CacheEvict(value = "employees", allEntries = true)
    public void uploadProfilePicture(MultipartFile file, String employeeId) {
        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("No employee found with ID:: " + employeeId));
        var profilePicture = fileStorageService.saveFile(file, employeeId);
        employee.setPicture(profilePicture);
        repository.save(employee);
    }

    @Cacheable(value = "employees", key = "#employeeId")
    public EmployeeResponse findById(String employeeId) {
        return repository.findById(employeeId)
                .map(mapper::toEmployeeResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException("No employee found with ID:: " + employeeId)
                );
    }
    @CacheEvict(value = "employees", allEntries = true)
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

    @CacheEvict(value = "employees", allEntries = true)
    public void archiveEmployee(String employeeId) {
        Employee employee = repository.findById(employeeId)
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Employee with ID %s was not found", employeeId))
                );
        employee.setArchived(true);
        repository.save(employee);
    }

    @Cacheable(value = "employees", key = "#departmentId")
    public PageResponse<EmployeeResponse> findEmployeesByDepartmentId(Integer page, Integer size, String departmentId) {
        Page<Employee> employeePage = repository.findAllByDepartmentId(departmentId, PageRequest.of(page, size));
        var employees = employeePage.getContent()
                .stream()
                .map(mapper::toEmployeeResponse)
                .toList();
        return PageResponse.<EmployeeResponse>builder()
                .page(employeePage.getNumber())
                .size(employeePage.getSize())
                .totalElements(employeePage.getTotalElements())
                .totalPages(employeePage.getTotalPages())
                .content(employees)
                .isFirst(employeePage.isFirst())
                .isLast(employeePage.isLast())
                .build();
    }
}
