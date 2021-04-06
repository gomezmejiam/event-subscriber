package co.com.ingeniods.humantalent.infrestructure.event.listener;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.jooq.DSLContext;
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

@ContextConfiguration(classes = { PersistenceConfig.class, DSLContext.class, JacksonConfiguration.class,
		EmployeeAdapter.class, CreateEmployeeListener.class, EmployeeValidator.class })
@SpringBootTest
@ActiveProfiles("test")
class EmployeeRepositoryTest {

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
	void testCountOneByByIdentification() {
		Long count = employeeRepository.countByIdentification(DocumentType.CEDULA_CIUDADANIA, "1");
		assertEquals(1, count);
	}
	
	@Test
	void testCountZereoByByIdentification() {
		Long count = employeeRepository.countByIdentification(DocumentType.CEDULA_CIUDADANIA, "10");
		assertEquals(0, count);
	}
	
	@Test
	void testFindByIdTypeAndIdNumber() {
		EmployeeDTO dto = employeeRepository.findByIdTypeAndIdNumber(DocumentType.CEDULA_CIUDADANIA, "1");
		assertEquals("1", dto.getIdNumber());
		assertEquals(DocumentType.CEDULA_CIUDADANIA, dto.getIdType());
		assertEquals("512916709388554621665590841409543270", dto.getId().toString());
	}


}
