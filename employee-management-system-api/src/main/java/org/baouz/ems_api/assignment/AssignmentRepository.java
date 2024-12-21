package org.baouz.ems_api.assignment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, String> {
    Page<Assignment> findAllByEmployeeId(String employeeId, Pageable pageable);
    Page<Assignment> findAllByProjectId(String projectId, Pageable pageable);

}
