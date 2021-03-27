package co.com.ingeniods.shared.validator.domain;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;

@Getter
public class ValidationError implements Serializable{

	private static final long serialVersionUID = 271715808502790147L;

	private final String id;
	private final String message;
	private final ValidationErrorCode code;

	public ValidationError(ValidationErrorCode code, String message) {
		id = UUID.randomUUID().toString();
		this.message = message;
		this.code = code;
	}

}
