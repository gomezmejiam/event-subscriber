package co.com.ingeniods.humantalent.infrestructure.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.ingeniods.humantalent.domain.service.ExistsEmployeeByPersonId;
import co.com.ingeniods.humantalent.application.service.EmployeeValidator;

@Configuration
public class ValidatorConfig {

	@Bean
	public EmployeeValidator employeeValidator(ExistsEmployeeByPersonId existsEmployeeByPersonId) {
		return new EmployeeValidator(existsEmployeeByPersonId);
	}

}
