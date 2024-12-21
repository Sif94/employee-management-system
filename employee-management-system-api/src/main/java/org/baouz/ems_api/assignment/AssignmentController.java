package org.baouz.ems_api.assignment;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("assignments")
@Tag(name = "Assignment")
public class AssignmentController {
    private final AssignmentService service;

    @PostMapping
    public ResponseEntity<String> saveAssignment(
            @RequestBody @Valid AssignmentRequest request,
            Authentication connectedUser
    ) throws MessagingException {
        return ResponseEntity.status(CREATED)
                .body(service.save(request, connectedUser));
    }
}
