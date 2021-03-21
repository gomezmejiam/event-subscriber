package co.com.ingeniods.shared.event;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import co.com.ingeniods.shared.event.domain.Event;
import co.com.ingeniods.shared.exception.domain.ValidationException;
import co.com.ingeniods.shared.validator.EntityValidator;
import co.com.ingeniods.shared.validator.domain.ValidationError;
import lombok.AccessLevel;

@Slf4j
@Getter
public abstract class EventProcessor<T> {
  
  private final String eventType;
  private final String name;
  
  @Getter(AccessLevel.PROTECTED)
  protected final EntityValidator<T> validator;
  
  private static final String NAME_PREFIX = "_LISTENER";
  
  
  public EventProcessor(String eventType, EntityValidator<T> validator){
    this.eventType = eventType;
    this.validator = validator;
    this.name = generateName(eventType);
  }

  private String generateName(String eventType) {
    return eventType.concat(NAME_PREFIX);
  }
  
  public void accept(Event<String> messageEvent) {
	  if (!canProcessType(messageEvent.getType())) {
			logEvent("{} ignore message {} {} ", messageEvent);
			return;
		}
	  logEvent("{} processing message {} {} ", messageEvent);
	  T entity = readValue(String.valueOf(messageEvent.getData()));
	  Event<T> entityEvent = new Event<T>(messageEvent.getType(),entity);
	  List<ValidationError> errorList = validator.validateError(entity);
		if (!Optional.ofNullable(errorList).orElse(Collections.emptyList()).isEmpty()) {
			throw new ValidationException(errorList);
		}
	  process(entityEvent);
  }
  
  private void logEvent(String pattern, Event<String> messageEvent) {
		log.info(pattern, this.getName(), messageEvent.getType(), messageEvent.getId());
	}


  public abstract void process(Event<T> messageEvent);
  
  public void validate(Event<String> messageEvent) {
    T entity = readValue(String.valueOf(messageEvent.getData()));
    this.getValidator().validate(entity);
  }
  
  public boolean canProcessType(String eventType) {
    return this.getEventType().equalsIgnoreCase(eventType);
  }
  
  public abstract T readValue(String content);
  
}
