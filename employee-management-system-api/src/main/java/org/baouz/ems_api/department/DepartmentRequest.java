package org.baouz.ems_api.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DepartmentRequest(
        String id,
        @NotNull(message = "Department name is mandatory")
        @NotBlank(message = "Department name is mandatory")
        @NotEmpty(message = "Department name is mandatory")
        String name,
        @NotNull(message = "Department description is mandatory")
        @NotBlank(message = "Department description is mandatory")
        @NotEmpty(message = "Department description is mandatory")
        String description,
        @NotNull(message = "Department location is mandatory")
        @NotBlank(message = "Department location is mandatory")
        @NotEmpty(message = "Department location is mandatory")
        String location
) {
}
