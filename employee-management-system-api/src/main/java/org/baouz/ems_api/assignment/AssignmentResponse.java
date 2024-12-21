package org.baouz.ems_api.assignment;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AssignmentResponse {
    private String id;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private String employeeId;
    private String projectId;
}
