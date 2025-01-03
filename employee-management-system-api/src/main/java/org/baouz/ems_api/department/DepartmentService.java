package org.baouz.ems_api.department;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;

    @CacheEvict(value = "departments", allEntries = true)
    public String save(DepartmentRequest request) {
        var department = mapper.toDepartment(request);
        return repository.save(department).getId();
    }

    @Cacheable(value = "departments", key = "'allDepartments'")
    public List<DepartmentResponse> findAll() {
        List<DepartmentResponse> departmentResponses = repository.findAll()
                .stream()
                .map(mapper::toDepartmentResponse)
                .collect(Collectors.toList());
        return departmentResponses;
    }

    @Cacheable(value = "departments", key = "#departmentId")
    public DepartmentResponse findById(String departmentId) {
        return repository.findById(departmentId)
                .map(mapper::toDepartmentResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException("Department with id " + departmentId + " not found")
                );
    }

    @CacheEvict(value = "departments", allEntries = true)
    public String updateDepartment(DepartmentRequest request) {
        repository.findById(request.id())
                .orElseThrow(
                        () -> new EntityNotFoundException("Department with id " + request.id() + " not found")
                );
        var department = mapper.toDepartment(request);
        return repository.save(department).getId();
    }

    @CacheEvict(value = "departments", allEntries = true)
    public void deleteDepartmentById(String departmentId) {
        repository.findById(departmentId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Department with id " + departmentId + " not found")
                );
        repository.deleteById(departmentId);
    }
}
