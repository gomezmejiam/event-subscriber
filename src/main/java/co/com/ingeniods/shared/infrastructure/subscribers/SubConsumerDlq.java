package co.com.ingeniods.shared.infrastructure.subscribers;

import java.util.function.Consumer;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.pubsub.v1.PubsubMessage;
import co.com.ingeniods.shared.event.domain.ErrorType;
import co.com.ingeniods.shared.event.domain.Event;
import co.com.ingeniods.shared.event.domain.EventError;
import co.com.ingeniods.shared.exception.base.BusinessException;
import co.com.ingeniods.shared.exception.base.TechnicalException;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class SubConsumerDlq implements SubConsumer {

	private static final Gson GSON = new Gson();

	private final PubSubTemplate pubSubTemplate;

	private final String SUBSCRIPTION;

	private final String DLQ;

	private final String ACCEPTED;

	private Consumer<Event<String>> proccess;

	public SubConsumerDlq(PubSubTemplate pubSubTemplate, String subscription, String dlq, String accepted) {
		this.pubSubTemplate = pubSubTemplate;
		SUBSCRIPTION = subscription;
		DLQ = dlq;
		ACCEPTED = accepted;
	}

	@Override
	public String subscription() {
		return SUBSCRIPTION;
	}

	@Override
	public void subscribe(Consumer<Event<String>> consumer) {
		this.proccess = consumer;
		pubSubTemplate.subscribe(this.subscription(), pubsubMessage -> consume(pubsubMessage));
		log.info("Suscribiendo {} a {}", subscription());

	}

	private void consume(BasicAcknowledgeablePubsubMessage pubsubMessage) {
		PubsubMessage message = pubsubMessage.getPubsubMessage();
		String messageData = message.getData().toStringUtf8();
		Event<String> event = GSON.fromJson(messageData, new TypeToken<Event<String>>() {
		}.getType());
		try {
			this.proccess.accept(event);
			this.pubSubTemplate.publish(ACCEPTED, GSON.toJson(event));
			pubsubMessage.ack();
		} catch (TechnicalException et) {
			log.error("Error técnico (mensaje reintentable): {}", et.getMessage());
			pubsubMessage.nack();
		} catch (BusinessException en) {
			killMessage(event, en, pubsubMessage);
		}
	}

	private void killMessage(Event<String> event, BusinessException en,
			BasicAcknowledgeablePubsubMessage acknowledgeable) {
		log.error("Error de negocio: {}", en.getMessage());
		log.debug("Evento error a DLQ: {}", event);
		this.pubSubTemplate.publish(DLQ, createEvent(event, en));
		acknowledgeable.ack();
	}

	private String createEvent(Event<String> event, BusinessException ex) {
		val errorEvent = new EventError(event, ErrorType.BUSINESS, ex);
		return GSON.toJson(errorEvent);
	}
}
