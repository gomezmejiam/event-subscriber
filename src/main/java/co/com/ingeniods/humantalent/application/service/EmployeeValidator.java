package co.com.ingeniods.humantalent.application.service;

import java.util.ArrayList;
import java.util.List;

import co.com.ingeniods.humantalent.domain.model.Employee;
import co.com.ingeniods.shared.validator.EntityValidator;
import co.com.ingeniods.shared.validator.domain.ValidationError;
import co.com.ingeniods.shared.validator.domain.ValidationErrorCode;

public class EmployeeValidator implements EntityValidator<Employee> {

	private EmployeeService employeeService;

	public EmployeeValidator(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Override
	public List<ValidationError> validateError(Employee entity) {
		List<ValidationError> errors = new ArrayList<>();
		if (employeeService.existsByPersonId(entity.getPerson().getId())) {
			String message = String.format("Employee with id %s ", entity.getPerson().getId());
			errors.add(getError(ValidationErrorCode.ENTITY_ALREADY_REGISTRED, message));
		}
		return errors;
	}

	private ValidationError getError(ValidationErrorCode code, String message) {
		return new ValidationError(code, message);
	}

}
