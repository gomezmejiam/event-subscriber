package co.com.ingeniods.humantalent.infrestructure.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.ingeniods.humantalent.domain.validator.EmployeeValidator;

@Configuration
public class ValidatorConfig {
  
  @Bean
  public EmployeeValidator employeeValidator() {
    return new EmployeeValidator();
  }

}
