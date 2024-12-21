package org.baouz.ems_api.department;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;

    public String save(DepartmentRequest request) {
        var department = mapper.toDepartment(request);
        return repository.save(department).getId();
    }

    public List<DepartmentResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDepartmentResponse)
                .toList();
    }

    public DepartmentResponse findById(String departmentId) {
        return repository.findById(departmentId)
                .map(mapper::toDepartmentResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException("Department with id " + departmentId + " not found")
                );
    }

    public String updateDepartment(DepartmentRequest request) {
        repository.findById(request.id())
                .orElseThrow(
                        () -> new EntityNotFoundException("Department with id " + request.id() + " not found")
                );
        var department = mapper.toDepartment(request);
        return repository.save(department).getId();
    }

    public void deleteDepartmentById(String departmentId) {
        repository.findById(departmentId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Department with id " + departmentId + " not found")
                );
        repository.deleteById(departmentId);
    }
}
