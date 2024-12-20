package org.baouz.ems_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EmployeeManagementSystemApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApiApplication.class, args);
	}

}
