package org.baouz.ems_api.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmployeeRequest(
        String id,
        @NotNull(message = "Employee firstname is mandatory")
        @NotBlank(message = "Employee firstname is mandatory")
        @NotEmpty(message = "Employee firstname is mandatory")
        String firstname,
        @NotNull(message = "Employee lastname is mandatory")
        @NotBlank(message = "Employee lastname is mandatory")
        @NotEmpty(message = "Employee lastname is mandatory")
        String lastname,
        @NotNull(message = "Employee email is mandatory")
        @NotBlank(message = "Employee email is mandatory")
        @NotEmpty(message = "Employee email is mandatory")
        String email,
        String phone,
        @NotNull(message = "Employee birthday is mandatory")
        LocalDate birthday,
        @NotNull(message = "Employee hireDate is mandatory")
        LocalDate hireDate,
        @NotNull(message = "Employee salary is mandatory")
        Double salary
) {
}
