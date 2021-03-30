package co.com.ingeniods;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import co.com.ingeniods.shared.event.EventProcessor;
import co.com.ingeniods.shared.validator.domain.ValidationError;
import co.com.ingeniods.shared.validator.domain.ValidationErrorCode;

@SpringBootTest
class EventSubscriberApplicationTests {

	@MockBean
    EventProcessor<?> eventProcessor;
	
	@Test
	void contextLoads() {
		ValidationError validation = new ValidationError(ValidationErrorCode.ENTITY_ALREADY_REGISTRED,"");
		assertNotNull(validation.getId());
	}

}
