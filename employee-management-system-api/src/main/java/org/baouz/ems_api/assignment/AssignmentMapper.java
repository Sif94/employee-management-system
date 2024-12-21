package org.baouz.ems_api.assignment;

import org.springframework.stereotype.Service;

@Service
public class AssignmentMapper {

    public Assignment toAssignment(AssignmentRequest request) {
        return Assignment.builder()
                .id(request.id())
                .role(request.role())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
    }

    public AssignmentResponse toAssignmentResponse(Assignment assignment) {
        return AssignmentResponse.builder()
                .id(assignment.getId())
                .role(assignment.getRole())
                .startDate(assignment.getStartDate())
                .endDate(assignment.getEndDate())
                .employeeId(assignment.getEmployee().getId())
                .projectId(assignment.getProject().getId())
                .build();
    }
}
