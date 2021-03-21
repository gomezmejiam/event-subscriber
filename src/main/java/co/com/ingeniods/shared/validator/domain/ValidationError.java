package co.com.ingeniods.shared.validator.domain;

import java.util.UUID;

import lombok.Getter;

@Getter
public abstract class ValidationError {

	private final String id;
	private final String message;
	private final ValidationErrorCode code;

	public ValidationError(ValidationErrorCode code, String message) {
		id = UUID.randomUUID().toString();
		this.message = message;
		this.code = code;
	}

}
