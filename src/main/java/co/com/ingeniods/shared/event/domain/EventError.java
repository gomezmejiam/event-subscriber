package co.com.ingeniods.shared.event.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import co.com.ingeniods.shared.exception.base.BaseException;
import co.com.ingeniods.shared.exception.domain.ValidationException;
import co.com.ingeniods.shared.validator.domain.ValidationError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventError extends Event<String> {
	private final List<ErrorInfo> details;

	public EventError(Event<?> event, ErrorType type, BaseException exception) {
		super(event.getType(), String.valueOf(event.getData()));
		addHeaders(event.getHeaders().entrySet());
		addHeader("ERROR_TYPE", type.name());
		if (exception instanceof ValidationException) {
			this.details = ((ValidationException) exception).getErrors().stream().map(this::getErrorInfo)
					.collect(Collectors.toList());
		} else {
			this.details = Arrays.asList(getErrorInfo(exception));
		}
	}

	private ErrorInfo getErrorInfo(BaseException exception) {
		return new ErrorInfo(exception.getExceptionCode().getType(), exception.getMessage(),
				exception.getExceptionCode().getCode());
	}

	private ErrorInfo getErrorInfo(ValidationError validationError) {
		return new ErrorInfo(validationError.getCode().getType(), validationError.getMessage(),
				validationError.getCode().getCode());
	}

}
