package org.baouz.ems_api.department;

import org.springframework.stereotype.Service;

@Service
public class DepartmentMapper {

    public Department toDepartment(DepartmentRequest request) {
        return Department.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .location(request.location())
                .build();
    }

    public DepartmentResponse toDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .location(department.getLocation())
                .build();
    }
}
