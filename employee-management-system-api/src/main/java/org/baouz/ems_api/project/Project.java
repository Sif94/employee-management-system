package org.baouz.ems_api.project;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.ems_api.assignment.Assignment;
import org.baouz.ems_api.common.BaseEntity;
import org.baouz.ems_api.department.Department;

import java.time.LocalDate;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(
        name = "projects",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "projects_unique_constraint_name",
                        columnNames = {"name"}
                )
        }
)
public class Project extends BaseEntity {
    @Id @GeneratedValue(strategy = UUID)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(STRING)
    private Status status;
    @ElementCollection
    private Set<String> tags;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToMany(mappedBy = "project")
    private Set<Assignment> assignments;
    @Column(columnDefinition = "boolean default false")
    private boolean isArchived = false;

}
