package org.baouz.ems_api.employee;

import java.time.LocalDate;

public record EmployeeRequest(
        String id,
        String firstname,
        String lastname,
        String email,
        String phone,
        LocalDate birthday,
        LocalDate hireDate,
        Double salary
) {
}
