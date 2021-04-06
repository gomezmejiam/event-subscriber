package co.com.ingeniods.config.jackson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfiguration {

	private final ObjectMapper mapper = new ObjectMapper();

	@Bean
	public ObjectMapper mapper() {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		mapper.registerModule(new Jdk8Module()).registerModule(new JavaTimeModule())
				.registerModule(new CustomJacksonModule());
		mapper.addMixIn(getClass(), getClass());
		return mapper;
	}

}
