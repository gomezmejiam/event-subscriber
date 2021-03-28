package co.com.ingeniods.humantalent.infrestructure.event.listener;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;

import java.math.BigInteger;
import java.util.Collections;

import org.jooq.DSLContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import co.com.ingeniods.config.jackson.JacksonConfiguration;
import co.com.ingeniods.humantalent.application.service.EmployeeValidator;
import co.com.ingeniods.humantalent.domain.model.DocumentType;
import co.com.ingeniods.humantalent.domain.model.Employee;
import co.com.ingeniods.humantalent.infrestructure.repository.adapter.EmployeeAdapter;
import co.com.ingeniods.humantalent.infrestructure.repository.dto.EmployeeDTO;
import co.com.ingeniods.humantalent.infrestructure.repository.port.EmployeeRepository;
import co.com.ingeniods.shared.event.domain.Event;
import co.com.ingeniods.shared.exception.domain.ValidationException;

@ContextConfiguration(classes = { JacksonConfiguration.class, EmployeeAdapter.class, CreateEmployeeListener.class,
		EmployeeValidator.class })
@SpringBootTest
class CreateEmployeeListenerTest {

	@Autowired
	private CreateEmployeeListener createEmployeeListener;

	@MockBean
	private EmployeeRepository employeeRepository;

	@MockBean
	private DSLContext dslContext;

	@BeforeEach
	void init() {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(new BigInteger("512916709388554621665590841409543273"));
		when(employeeRepository.findByIdentification("CEDULA_CIUDADANIA", "1")).thenReturn(dto);
		when(employeeRepository.findByIdentification("CEDULA_CIUDADANIA", "2")).thenReturn(null);
		when(employeeRepository.findByIdentification("CEDULA_CIUDADANIA", "3")).thenReturn(new EmployeeDTO());
	}

	@Test
	void testReadValueString() {
		String data = "{\"employee\":{\"id\":512916709388554621665590841409543273,\"person\":{\"id\":{\"type\":\"CC\",\"number\":\"1\"},\"name\":{\"firtsName\":\"A\",\"middleName\":\"B\",\"surname\":\"C\",\"secondSurname\":\"D\"},\"birthDate\":null}}}";
		Employee algo = createEmployeeListener.readValue(data);
		assertNotNull(algo);
	}

	@Test
	void testAcceptEventAlreadyExist() {
		String data = "{\"employee\":{\"id\":512916709388554621665590841409543273,\"person\":{\"id\":{\"type\":\"CC\",\"number\":\"1\"},\"name\":{\"firtsName\":\"A\",\"middleName\":\"B\",\"surname\":\"C\",\"secondSurname\":\"D\"},\"birthDate\":null}}}";
		Assertions.assertThrows(ValidationException.class,
				() -> createEmployeeListener.accept(new Event<String>("CREATE_EMPLOYEE", data)));
		verify(employeeRepository, times(1)).findByIdentification("CEDULA_CIUDADANIA", "1");
	}

	@Test
	void testAcceptEventExistNull() {
		String data = "{\"employee\":{\"id\":512916709388554621665590841409543273,\"person\":{\"id\":{\"type\":\"CC\",\"number\":\"2\"},\"name\":{\"firtsName\":\"A\",\"middleName\":\"B\",\"surname\":\"C\",\"secondSurname\":\"D\"},\"birthDate\":null}}}";
		createEmployeeListener.accept(new Event<String>("CREATE_EMPLOYEE", data));
		verify(employeeRepository, times(1)).findByIdentification("CEDULA_CIUDADANIA", "2");
	}

	@Test
	void testAcceptEventExistIdNull() {
		String data = "{\"employee\":{\"id\":512916709388554621665590841409543273,\"person\":{\"id\":{\"type\":\"CC\",\"number\":\"3\"},\"name\":{\"firtsName\":\"A\",\"middleName\":\"B\",\"surname\":\"C\",\"secondSurname\":\"D\"},\"birthDate\":null}}}";
		createEmployeeListener.accept(new Event<String>("CREATE_EMPLOYEE", data));
		verify(employeeRepository, times(1)).findByIdentification("CEDULA_CIUDADANIA", "3");
		verify(employeeRepository, times(1)).save(Mockito.any());
	}

}
