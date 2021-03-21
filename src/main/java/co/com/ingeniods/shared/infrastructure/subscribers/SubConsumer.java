package co.com.ingeniods.shared.infrastructure.subscribers;

import java.util.function.Consumer;

import co.com.ingeniods.shared.event.domain.Event;

public interface SubConsumer {

	String subscription();

	void subscribe(Consumer<Event<String>> proccess);

}
