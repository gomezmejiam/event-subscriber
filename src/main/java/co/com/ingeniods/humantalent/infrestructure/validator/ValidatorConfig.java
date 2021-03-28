package co.com.ingeniods.humantalent.infrestructure.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.ingeniods.humantalent.application.service.EmployeeService;
import co.com.ingeniods.humantalent.application.service.EmployeeValidator;

@Configuration
public class ValidatorConfig {

	@Bean
	public EmployeeValidator employeeValidator(EmployeeService employeeService) {
		return new EmployeeValidator(employeeService);
	}

}
