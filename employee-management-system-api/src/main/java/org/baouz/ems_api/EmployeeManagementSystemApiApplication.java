package org.baouz.ems_api;

import org.baouz.ems_api.assignment.Assignment;
import org.baouz.ems_api.assignment.AssignmentRepository;
import org.baouz.ems_api.department.Department;
import org.baouz.ems_api.department.DepartmentRepository;
import org.baouz.ems_api.employee.Employee;
import org.baouz.ems_api.employee.EmployeeRepository;
import org.baouz.ems_api.project.Project;
import org.baouz.ems_api.project.ProjectRepository;
import org.baouz.ems_api.role.Role;
import org.baouz.ems_api.role.RoleName;
import org.baouz.ems_api.role.RoleRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.baouz.ems_api.project.Status.PENDING;

@SpringBootApplication
@EnableJpaAuditing
//@EnableAsync
@EnableCaching
@EnableRabbit
public class EmployeeManagementSystemApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(
			DepartmentRepository departmentRepository,
			EmployeeRepository employeeRepository,
			ProjectRepository projectRepository,
			AssignmentRepository assignmentRepository,
			RoleRepository roleRepository,
			PasswordEncoder passwordEncoder
	) {
		return args -> {
			Role admin = roleRepository.save(
					Role.builder()
							.name(RoleName.ADMIN)
							.description("Admin")
							.createdBy("John")
							.build()
			);
			roleRepository.save(
					Role.builder()
							.name(RoleName.MANAGER)
							.description("Manager")
							.createdBy("John")
							.build()
			);
			roleRepository.save(
					Role.builder()
							.name(RoleName.EMPLOYEE)
							.description("Employee")
							.createdBy("John")
							.build()
			);
			roleRepository.save(
					Role.builder()
							.name(RoleName.HR)
							.description("HR")
							.createdBy("John")
							.build()
			);
			roleRepository.save(
					Role.builder()
							.name(RoleName.SUPER_ADMIN)
							.description("Super Admin")
							.createdBy("John")
							.build()
			);
			Department department = departmentRepository.save(
					Department.builder()
							.name("Department 1")
							.description("Department 1")
							.location("Department 1")
							.createdBy("John Doe")
							.build()
			);
			Employee employee = employeeRepository.save(
					Employee.builder()
							.firstname("First Name")
							.lastname("Last Name")
							.email("email@email.com")
							.birthday(LocalDate.now())
							.hireDate(LocalDate.now())
							.createdBy("John Doe")
							.department(department)
							.salary(24000.99)
							.role(admin)
							.password(passwordEncoder.encode("password"))
							.build()
			);
			Project project = projectRepository.save(
					Project.builder()
							.name("Project 1")
							.description("Project 1")
							.status(PENDING)
							.startDate(LocalDate.now())
							.endDate(LocalDate.now())
							.tags(List.of("Java", "Web"))
							.createdBy("John Doe")
							.department(department)
							.build()
			);
			assignmentRepository.save(
					Assignment.builder()
							.role("Web Dev")
							.startDate(LocalDate.now())
							.endDate(LocalDate.now())
							.employee(employee)
							.project(project)
							.createdBy("John Doe")
							.build()
			);
		};
	}
}
