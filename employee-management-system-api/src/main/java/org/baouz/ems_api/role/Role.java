package org.baouz.ems_api.role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.ems_api.common.BaseEntity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(
        name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "roles_unique_constraints_name",
                        columnNames = {"name"}
                )
        }
)
public class Role extends BaseEntity {
    @Id @GeneratedValue(strategy = UUID)
    private String id;
    @Column(nullable = false)
    @Enumerated(STRING)
    private RoleName name;
    private String description;

}
