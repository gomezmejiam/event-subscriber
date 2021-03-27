package co.com.ingeniods.config;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.com.ingeniods.shared.event.EventProcessor;
import co.com.ingeniods.shared.event.domain.Event;
import co.com.ingeniods.shared.infrastructure.subscribers.SubConsumerDlq;
import co.com.ingeniods.shared.infrastructure.subscribers.SubConsumerFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Profile({ "production" })
public class SubscribersConfig {

	@Autowired
	private SubConsumerFactory consumerFactory;

	@Autowired
	private ObjectMapper mapper;

	@Value("${events.integration.events}")
	private String queue;

	@Value("${events.integration.dlq}")
	private String dlq;

	@Value("${events.integration.notify}")
	private String accepted;
	
	@Value("${events.integration.executors}")
	private Integer executors;

	@SuppressWarnings("rawtypes")
	@Autowired
	List<EventProcessor> eventProcessors;
	
	private ExecutorService executor;

	@EventListener(ApplicationReadyEvent.class)
	public void subscribtions() {
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm"));
		subscribe(queue, dlq, accepted);
		executor = Executors.newFixedThreadPool(executors);
	}

	private void subscribe(String queue, String dlq, String accepted) {
		log.info("subscribtion for queue: " + queue);
		try {
			SubConsumerDlq subConsumerDlq = consumerFactory.getSubConsumerDlq(queue, dlq, accepted);
			subConsumerDlq.subscribe(defaultConsumer());
		} catch (Exception ex) {
			log.error("subscribtion for queue: " + queue, ex.getMessage());
		}
	}

	private Consumer<Event<String>> defaultConsumer() {
		return event -> {
			@SuppressWarnings("rawtypes")
			final Optional<EventProcessor> eventProcessor = eventProcessors.stream()
					.filter(x -> x.canProcessType(event.getType())).findFirst();

			if (!eventProcessor.isPresent()) {
				log.error("Processor for type {} not found", event.getType());
				return;
			}

			EventProcessor<?> processor = eventProcessor.get();
			log.info(" {} elegido para procesar el evento {}:({})", processor.getClass().getSimpleName(), event.getType(),
					event.getId());
			executor.execute(() -> processor.accept(event));
		};
	}

}
