package co.com.ingeniods.shared.event;

import co.com.ingeniods.shared.event.domain.Event;

public interface EventPublisher {
  
  public void publishEvent(Event<?> event);

}
