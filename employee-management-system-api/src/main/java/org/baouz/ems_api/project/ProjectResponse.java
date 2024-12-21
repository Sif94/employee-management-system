package org.baouz.ems_api.project;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectResponse {
    private String id;
    private String name;
    private String description;
    private Status status;
    private String departmentId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<String> tags;
}
