package org.baouz.ems_api.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, String> {
    Optional<Project> findByName(String name);
    Page<Project> findByDepartmentId(String departmentId, Pageable pageable);
    List<Project> findByStartDate(LocalDate startDate);
    List<Project> findByEndDate(LocalDate endDate);
    Page<Project> findAllByIsArchivedIsFalse(Pageable pageable);

}
