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
import org.baouz.ems_api.role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
public class Employee extends BaseEntity implements UserDetails, Principal {

    @Id @GeneratedValue(strategy = UUID)
    private String id;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String phone;
    private LocalDate birthday;
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
    @ManyToOne
    private Role role;

    public String getFullName(){
        return firstname + " " + lastname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName().name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public String getName() {
        return this.email;
    }
}
