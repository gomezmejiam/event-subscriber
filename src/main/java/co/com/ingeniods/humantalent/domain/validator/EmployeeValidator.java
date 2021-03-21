package co.com.ingeniods.humantalent.domain.validator;

import java.util.Collections;
import java.util.List;

import co.com.ingeniods.humantalent.domain.model.Employee;
import co.com.ingeniods.shared.validator.EntityValidator;
import co.com.ingeniods.shared.validator.domain.ValidationError;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeValidator implements EntityValidator<Employee> {

	public EmployeeValidator() {
	}

	@Override
	public boolean validate(Employee entity) {
		log.info("validate {}", entity.getId());
		return Boolean.FALSE;
	}

	@Override
	public List<ValidationError> validateError(Employee entity) {
		return Collections.emptyList();
	}

}
