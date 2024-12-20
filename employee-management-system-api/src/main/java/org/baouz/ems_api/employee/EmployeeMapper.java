package org.baouz.ems_api.employee;

import org.baouz.ems_api.file.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {


    public Employee toEmployee(EmployeeRequest request) {
        return Employee.builder()
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .phone(request.phone())
                .birthday(request.birthday())
                .hireDate(request.hireDate())
                .salary(request.salary())
                .isArchived(false)
                .build();
    }

    public EmployeeResponse toEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstname(employee.getFirstname())
                .lastname(employee.getLastname())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .birthday(employee.getBirthday())
                .hireDate(employee.getHireDate())
                .picture(FileUtils.readFileFromLocation(employee.getPicture()))
                .salary(employee.getSalary())
                .build();
    }
}
