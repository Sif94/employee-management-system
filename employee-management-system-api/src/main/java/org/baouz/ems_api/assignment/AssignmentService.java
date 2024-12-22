package org.baouz.ems_api.assignment;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baouz.ems_api.common.PageResponse;
import org.baouz.ems_api.email.EmailDTO;
import org.baouz.ems_api.email.EmailService;
import org.baouz.ems_api.email.EmailTemplateName;
import org.baouz.ems_api.employee.Employee;
import org.baouz.ems_api.employee.EmployeeRepository;
import org.baouz.ems_api.project.Project;
import org.baouz.ems_api.project.ProjectRepository;
import org.baouz.ems_api.publisher.RabbitMQJsonProducer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentService {
    private final AssignmentRepository repository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final RabbitMQJsonProducer producer;
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

        Assignment savedAssignment = repository.save(assignment);
        senEmail(savedAssignment, "Project Assignment");
        return savedAssignment.getId();
    }


    public PageResponse<AssignmentResponse> findAllAssignmentsByProjectId(int page, int size, String projectId) {
        var pageable = PageRequest.of(page, size);
        Page<Assignment> assignmentPage = repository.findAllByProjectId(projectId, pageable);
        List<AssignmentResponse> assignmentResponses = assignmentPage.getContent()
                .stream()
                .map(mapper::toAssignmentResponse)
                .toList();
        return PageResponse.<AssignmentResponse>builder()
                .page(assignmentPage.getNumber())
                .size(assignmentPage.getSize())
                .totalPages(assignmentPage.getTotalPages())
                .totalElements(assignmentPage.getTotalElements())
                .content(assignmentResponses)
                .isFirst(assignmentPage.isFirst())
                .isLast(assignmentPage.isLast())
                .build();
    }

    public AssignmentResponse findAssignmentById(String assignmentId) {
        return repository.findById(assignmentId)
                .map(mapper::toAssignmentResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException("Assignment not found")
                );
    }

    public String updateAssignment(AssignmentRequest request) throws MessagingException {
        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Employee not found")
                );
        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Project not found")
                );
        var assignment = mapper.toAssignment(request);

        assignment.setEmployee(employee);
        assignment.setProject(project);


        Assignment savedAssignment = repository.save(assignment);
        senEmail(savedAssignment, "Update Project Assignment");

        return savedAssignment.getId();

    }

    private void senEmail(Assignment assignment, String subject) throws MessagingException {
        Map<String, Object> properties = new HashMap<>();
        properties.put("fullName", assignment.getEmployee().getFullName());
        properties.put("projectName", assignment.getProject().getName());
        properties.put("role", assignment.getRole());
        properties.put("startDate", assignment.getStartDate());
        properties.put("endDate", assignment.getEndDate());

        EmailDTO emailDTO = EmailDTO.builder()
                .to(assignment.getEmployee().getEmail())
                .from("noreply@ems.com")
                .emailTemplate(EmailTemplateName.ASSIGNMENT_NOTIFICATION)
                .subject(subject)
                .properties(properties)
                .build();
        producer.sendJsonMessage(emailDTO);
    }

    public void deleteAssignmentById(String assignmentId) {
        repository.deleteById(assignmentId);
    }

    public PageResponse<AssignmentResponse> findAllAssignmentsByEmployeeId(int page, int size, String employeeId) {
        Page<Assignment> assignmentPage = repository.findAllByEmployeeId(employeeId, PageRequest.of(page, size));

        List<AssignmentResponse> assignmentResponses = assignmentPage.getContent()
                .stream()
                .map(mapper::toAssignmentResponse)
                .toList();
        return PageResponse.<AssignmentResponse>builder()
                .page(assignmentPage.getNumber())
                .size(assignmentPage.getSize())
                .totalPages(assignmentPage.getTotalPages())
                .totalElements(assignmentPage.getTotalElements())
                .content(assignmentResponses)
                .isFirst(assignmentPage.isFirst())
                .isLast(assignmentPage.isLast())
                .build();
    }
}
