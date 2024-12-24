package org.baouz.ems_api.project;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
    private List<String> tags;
}
