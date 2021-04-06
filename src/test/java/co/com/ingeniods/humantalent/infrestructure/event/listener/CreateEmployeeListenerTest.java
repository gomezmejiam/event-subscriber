package co.com.ingeniods.humantalent.infrestructure.event.listener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigInteger;

import org.jooq.DSLContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import co.com.ingeniods.config.PersistenceConfig;
import co.com.ingeniods.config.jackson.JacksonConfiguration;
import co.com.ingeniods.humantalent.application.service.EmployeeValidator;
import co.com.ingeniods.humantalent.domain.model.DocumentType;
import co.com.ingeniods.humantalent.domain.model.Employee;
import co.com.ingeniods.humantalent.infrastructure.event.listener.CreateEmployeeListener;
import co.com.ingeniods.humantalent.infrastructure.repository.adapter.EmployeeAdapter;
import co.com.ingeniods.humantalent.infrastructure.repository.assembler.EmployeeAssembler;
import co.com.ingeniods.humantalent.infrastructure.repository.dto.EmployeeDTO;
import co.com.ingeniods.humantalent.infrastructure.repository.port.EmployeeRepository;
import co.com.ingeniods.shared.event.domain.Event;
import co.com.ingeniods.shared.exception.domain.ValidationException;
import co.com.ingeniods.shared.exception.events.UnreadableEventException;

@ContextConfiguration(classes = { PersistenceConfig.class, DSLContext.class, JacksonConfiguration.class,
		EmployeeAdapter.class, CreateEmployeeListener.class, EmployeeValidator.class })
@SpringBootTest
@ActiveProfiles("test")
class CreateEmployeeListenerTest {

	@Autowired
	private CreateEmployeeListener createEmployeeListener;

	@Autowired
	private EmployeeRepository employeeRepository;

	@MockBean
	private DSLContext dslContext;

	@BeforeEach
	void init() {
		EmployeeDTO dto = getEmployee(new BigInteger("512916709388554621665590841409543270"));
		employeeRepository.save(dto);
	}

	private EmployeeDTO getEmployee(BigInteger id) {
		String data = "{\"employee\":{\"id\":1,\"person\":{\"id\":{\"type\":\"CC\",\"number\":\"1\"},\"name\":{\"firtsName\":\"A\",\"middleName\":\"B\",\"surname\":\"C\",\"secondSurname\":\"D\"},\"birthDate\":null}}}";
		Employee entity = createEmployeeListener.readValue(data);
		EmployeeDTO dto = EmployeeAssembler.INSTANCE.toDto(entity);
		dto.setId(id);
		return dto;
	}

	@Test
	void testReadValueStringWithId() {
		String data = "{\"employee\":{\"id\":512916709388554621665590841409543271,\"person\":{\"id\":{\"type\":\"CC\",\"number\":\"1\"},\"name\":{\"firtsName\":\"A\",\"middleName\":\"B\",\"surname\":\"C\",\"secondSurname\":\"D\"},\"birthDate\":null}}}";
		Employee entity = createEmployeeListener.readValue(data);
		assertNotNull(entity.getId());
	}

	@Test
	void testReadValueStringWithoutZeroId() {
		String data = "{\"employee\":{\"id\":0,\"person\":{\"id\":{\"type\":\"CC\",\"number\":\"1\"},\"name\":{\"firtsName\":\"A\",\"middleName\":\"B\",\"surname\":\"C\",\"secondSurname\":\"D\"},\"birthDate\":null}}}";
		Assertions.assertThrows(UnreadableEventException.class, () -> createEmployeeListener.readValue(data));
	}

	@Test
	void testReadValueStringWithoutId() {
		String data = "{\"employee\":{\"id\":null,\"person\":{\"id\":{\"type\":\"CC\",\"number\":\"1\"},\"name\":{\"firtsName\":\"A\",\"middleName\":\"B\",\"surname\":\"C\",\"secondSurname\":\"D\"},\"birthDate\":null}}}";
		Employee entity = createEmployeeListener.readValue(data);
		assertNotNull(entity.getId());
	}
	
	@Test
	void testAcceptEventAlreadyExist() {
		String data = "{\"employee\":{\"id\":512916709388554621665590841409543272,\"person\":{\"id\":{\"type\":\"CC\",\"number\":\"1\"},\"name\":{\"firtsName\":\"A\",\"middleName\":\"B\",\"surname\":\"C\",\"secondSurname\":\"D\"},\"birthDate\":null}}}";
		Assertions.assertThrows(ValidationException.class,
				() -> createEmployeeListener.accept(new Event<String>("CREATE_EMPLOYEE", data)));
	}

	@Test
	void testAcceptEventExistNull() {
		String data = "{\"employee\":{\"id\":51291670938855462166559084140954323,\"person\":{\"id\":{\"type\":\"CC\",\"number\":\"2\"},\"name\":{\"firtsName\":\"A\",\"middleName\":\"B\",\"surname\":\"C\",\"secondSurname\":\"D\"},\"birthDate\":null}}}";
		createEmployeeListener.accept(new Event<String>("CREATE_EMPLOYEE", data));
		EmployeeDTO dto = employeeRepository.findByIdTypeAndIdNumber(DocumentType.CEDULA_CIUDADANIA, "2");
		assertEquals("2", dto.getIdNumber());
		assertEquals("51291670938855462166559084140954323", dto.getId().toString());
	}

}
