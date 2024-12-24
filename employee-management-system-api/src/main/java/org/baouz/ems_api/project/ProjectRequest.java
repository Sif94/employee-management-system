package org.baouz.ems_api.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record ProjectRequest(
        String id,
        @NotNull(message = "Project name is mandatory")
        @NotEmpty(message = "Project name is mandatory")
        @NotBlank(message = "Project name is mandatory")
        String name,
        @NotNull(message = "Project description is mandatory")
        @NotEmpty(message = "Project description is mandatory")
        @NotBlank(message = "Project description is mandatory")
        String description,
        @NotNull(message = "Project status is mandatory")
        Status status,
        @NotNull(message = "Project start date is mandatory")
        LocalDate startDate,
        @NotNull(message = "Project end date is mandatory")
        LocalDate endDate,
        List<String> tags,
        @NotNull(message = "Project department is mandatory")
        @NotEmpty(message = "Project department is mandatory")
        @NotBlank(message = "Project department is mandatory")
        String departmentId
) {
}
