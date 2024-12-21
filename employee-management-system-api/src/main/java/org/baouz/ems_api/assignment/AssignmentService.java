package org.baouz.ems_api.assignment;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.baouz.ems_api.email.EmailDTO;
import org.baouz.ems_api.email.EmailService;
import org.baouz.ems_api.email.EmailTemplateName;
import org.baouz.ems_api.employee.Employee;
import org.baouz.ems_api.employee.EmployeeRepository;
import org.baouz.ems_api.project.Project;
import org.baouz.ems_api.project.ProjectRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final EmailService emailService;
    private final AssignmentMapper mapper;

    public String save(AssignmentRequest request, Authentication connectedUser) throws MessagingException {
        var assignment = mapper.toAssignment(request);
        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Employee not found")
                );
        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Project not found")
                );
        assignment.setEmployee(employee);
        assignment.setProject(project);

        Map<String, Object> properties = new HashMap<>();
        properties.put("fullName", employee.getFullName());
        properties.put("projectName", project.getName());
        properties.put("role", assignment.getRole());
        properties.put("startDate", assignment.getStartDate());
        properties.put("endDate", assignment.getEndDate());

        EmailDTO emailDTO = EmailDTO.builder()
                .to(employee.getEmail())
                .from("noreply@ems.com")
                .emailTemplate(EmailTemplateName.ASSIGNMENT_NOTIFICATION)
                .subject("Project Assignment")
                .properties(properties)
                .build();

        Assignment savedAssignment = assignmentRepository.save(assignment);
        emailService.sendEmail(emailDTO);
        return savedAssignment.getId();
    }
}
