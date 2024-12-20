package org.baouz.ems_api.employee;

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

import static jakarta.persistence.GenerationType.UUID;
import static jakarta.persistence.TemporalType.DATE;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(
        name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "employees_unique_constraint_email",
                        columnNames = {"email"}
                ),
                @UniqueConstraint(
                        name = "employees_unique_constraint_phone",
                        columnNames = {"phone"}
                )
        }
)
public class Employee extends BaseEntity {

    @Id @GeneratedValue(strategy = UUID)
    private String id;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private String email;
    private String phone;
    @Temporal(DATE)
    private LocalDate birthday;
    @Temporal(DATE)
    private LocalDate hireDate;
    @Column(nullable = false)
    private Double salary;
    private String picture;
    @Column(columnDefinition = "boolean default false")
    private boolean isArchived = false;
    @ManyToOne
    private Department department;
    @OneToMany(mappedBy = "employee")
    private Set<Assignment> assignments;

    public String getFullName(){
        return firstname + " " + lastname;
    }
}
