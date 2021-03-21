package co.com.ingeniods.shared.validator;

import java.util.List;
import org.springframework.stereotype.Component;

import co.com.ingeniods.shared.exception.events.InactiveFunctionException;
import co.com.ingeniods.shared.validator.domain.ValidationError;

@Component
public class NoopValidator implements EntityValidator<Object> {

  @Override
  public boolean validate(Object entity) {
    throw getException(entity);
  }

  @Override
  public List<ValidationError> validateError(Object entity) {
	  throw getException(entity);
  }
  
  private InactiveFunctionException getException(Object entity) {
	  return new InactiveFunctionException(String.format("Validator for %s is not implemented", entity.getClass().getSimpleName()));
  }

}
