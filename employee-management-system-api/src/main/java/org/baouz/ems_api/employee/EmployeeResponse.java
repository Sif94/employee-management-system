package org.baouz.ems_api.employee;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private LocalDate birthday;
    private LocalDate hireDate;
    private Double salary;
    private byte[] picture;
    private String departmentId;
}
