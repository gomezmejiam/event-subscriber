package co.com.ingeniods.shared.infrastructure.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ingeniods.shared.event.EventProcessor;
import co.com.ingeniods.shared.event.domain.Event;
import co.com.ingeniods.shared.exception.base.BusinessException;
import co.com.ingeniods.shared.exception.events.UnprocessedEventException;
import co.com.ingeniods.shared.infrastructure.api.resource.EventResource;
import co.com.ingeniods.shared.infrastructure.api.response.EventResponse;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/command")
public class CommandRestController {

	@SuppressWarnings("rawtypes")
	@Autowired
	List<EventProcessor> eventProcessors;

	@Autowired
	private ObjectMapper mapper;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/excecute")
	public ResponseEntity excecute(@RequestBody EventResource resource) {
		log.info("apply event: " + resource);
		Optional<EventProcessor> processor = getEventProcessor(resource);
		if (processor.isEmpty()) {
			return new ResponseEntity<>("NO_PROCESSOR_FOUND", HttpStatus.NOT_ACCEPTABLE);
		}
		EventResponse result = processMessage(resource, processor.get());
		log.info("applied event: {}", result);
		return new ResponseEntity(result, HttpStatus.OK);

	}

	@SuppressWarnings({ "rawtypes" })
	@PostMapping("/validate")
	public ResponseEntity<String> validate(@RequestBody EventResource resource) {
		log.info("validate event: " + resource);
		Optional<EventProcessor> processor = getEventProcessor(resource);
		if (processor.isEmpty()) {
			return new ResponseEntity<>("NO_PROCESSOR_FOUND", HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>(String.valueOf(validateMessage(resource, processor.get())), HttpStatus.OK);
	}

	private String getEventType(EventResource resource) {
		return resource.getHeaders().get("EVENT_TYPE");
	}

	private boolean validateMessage(EventResource resource, EventProcessor<?> processor) {
		Event<String> event = readEvent(resource);
		logProcessor(processor, event);
		return processor.validate(event);
	}

	private EventResponse processMessage(EventResource resource, EventProcessor<?> processor) {
		Event<String> event = readEvent(resource);
		logProcessor(processor, event);
		try {
			processor.accept(event);
			return getEventEntity(event.getId(), resource);
		} catch (Exception exception) {
			exceptionHandler(event, getDebugMode(resource), exception);
			return getEventEntity(event.getId(), resource, exception);
		}
	}

	private void logProcessor(EventProcessor<?> processor, Event<String> event) {
		log.info("Processor {} elegido para procesar el evento {} {}", processor.getClass().getSimpleName(),
				event.getType(), event.getId());
	}

	private Event<String> readEvent(EventResource resource) {
		String eventType = getEventType(resource);
		return new Event<>(eventType, resource.getHeaders(), resource.getData());
	}

	private Boolean getDebugMode(EventResource resource) {
		return Boolean.valueOf(resource.getHeaders().getOrDefault("DEBUG_MODE", "true"));
	}

	private EventResponse getEventEntity(String id, EventResource resource, Exception exception) {
		val eventEntity = getEventEntity(id, resource);
		eventEntity.setError(exception.getMessage());
		return eventEntity;
	}

	private EventResponse getEventEntity(String id, EventResource resource) {
		val eventEntity = new EventResponse();
		eventEntity.setId(id);
		try {
			eventEntity.setData(mapper.writeValueAsString(resource));
		} catch (JsonProcessingException jpe) {
			log.error(jpe.getMessage());
		}
		return eventEntity;
	}

	@SuppressWarnings("rawtypes")
	private Optional<EventProcessor> getEventProcessor(EventResource event) {
		return eventProcessors.stream().filter(x -> x.canProcessType(getEventType(event))).findFirst();
	}

	private void exceptionHandler(Event<String> event, boolean debug, Exception exception) {
		log.error("{}: {}", exception.getClass().getSimpleName(), exception.getMessage());
		if (debug) {
			log.error(event.getData());
		}

		if (exception instanceof BusinessException) {
			BusinessException be = (BusinessException) exception;
			log.error("{} {} {}", be.getExceptionCode().getCode(), be.getExceptionCode().getType(),
					exception.getMessage());
		} else if (!debug) {
			throw new UnprocessedEventException(exception.getMessage());
		}
	}

}
