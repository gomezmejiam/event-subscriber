package co.com.ingeniods.shared.exception.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import co.com.ingeniods.shared.exception.base.BusinessException;
import co.com.ingeniods.shared.exception.base.ExceptionCode;
import co.com.ingeniods.shared.validator.domain.ValidationError;
import lombok.Getter;

public class ValidationException extends BusinessException {

	private static final long serialVersionUID = -9160859154470139819L;

	@Getter
	private final List<ValidationError> errors;

	public ValidationException(List<ValidationError> errors) {
		super(ExceptionCode.VALIDATION_EXCEPTION, Objects.toString(errors));
		this.errors = Collections.unmodifiableList(errors);
	}

}
