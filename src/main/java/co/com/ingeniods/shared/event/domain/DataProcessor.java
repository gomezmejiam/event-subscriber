package co.com.ingeniods.shared.event.domain;

import co.com.ingeniods.shared.validator.EntityValidator;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class DataProcessor<T> {
    
  @Getter(AccessLevel.PROTECTED)
  protected final EntityValidator<T> validator;
  
  public DataProcessor(EntityValidator<T> validator) {
    this.validator = validator;
  }

  public void validate(T entity) {
    this.getValidator().validate(entity);
  }

}
