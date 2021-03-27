package co.com.ingeniods.shared.validator;

import java.util.List;

import co.com.ingeniods.shared.validator.domain.ValidationError;

public interface EntityValidator <T> {
  
	public default boolean validate(T entity) {
		return validateError(entity).isEmpty();
	}

  List<ValidationError> validateError(T entity);

}
