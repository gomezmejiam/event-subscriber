package co.com.ingeniods.shared.validator;

import java.util.List;

import co.com.ingeniods.shared.validator.domain.ValidationError;

public interface EntityValidator <T> {
  
  boolean validate(T entity);
  List<ValidationError> validateError(T entity);

}
