package org.baouz.ems_api.assignment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AssignmentRequest(
        String id,
        @NotNull(message = "Assignment role is mandatory")
        @NotBlank(message = "Assignment role is mandatory")
        @NotEmpty(message = "Assignment role is mandatory")
        String role,
        @NotNull(message = "Assignment start date is mandatory")
        LocalDate startDate,
        @NotNull(message = "Assignment end date is mandatory")
        LocalDate endDate,
        @NotNull(message = "Assignment employee id is mandatory")
        @NotBlank(message = "Assignment employee id is mandatory")
        @NotEmpty(message = "Assignment employee id is mandatory")
        String employeeId,
        @NotNull(message = "Assignment project id is mandatory")
        @NotBlank(message = "Assignment project id is mandatory")
        @NotEmpty(message = "Assignment project id is mandatory")
        String projectId
) {
}
