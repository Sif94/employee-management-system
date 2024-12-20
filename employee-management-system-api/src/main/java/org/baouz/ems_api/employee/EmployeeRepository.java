package org.baouz.ems_api.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByPhone(String phone);

}
