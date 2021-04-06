package co.com.ingeniods.humantalent.infrestructure.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;

import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ingeniods.config.PersistenceConfig;
import co.com.ingeniods.config.jackson.JacksonConfiguration;
import co.com.ingeniods.humantalent.application.service.EmployeeValidator;
import co.com.ingeniods.humantalent.domain.model.Employee;
import co.com.ingeniods.humantalent.infrastructure.api.EmployeeController;
import co.com.ingeniods.humantalent.infrastructure.event.listener.CreateEmployeeListener;
import co.com.ingeniods.humantalent.infrastructure.repository.adapter.EmployeeAdapter;
import co.com.ingeniods.humantalent.infrastructure.repository.assembler.EmployeeAssembler;
import co.com.ingeniods.humantalent.infrastructure.repository.dto.EmployeeDTO;
import co.com.ingeniods.humantalent.infrastructure.repository.port.EmployeeRepository;

@AutoConfigureMockMvc(addFilters = true)
@ContextConfiguration(classes = { PersistenceConfig.class, DSLContext.class, JacksonConfiguration.class,
		CreateEmployeeListener.class, EmployeeAdapter.class, EmployeeValidator.class, EmployeeController.class })
@WebMvcTest(includeFilters = @Filter(classes = { Repository.class }))
@ActiveProfiles("test")
class EmployeeControllerTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private CreateEmployeeListener createEmployeeListener;

	@MockBean
	private DSLContext dslContext;

	@Autowired
	private MockMvc restApi;

	@Autowired
	private ObjectMapper mapper;

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
	void getEmployee() throws Exception {
		String response = restApi.perform(MockMvcRequestBuilders.get("/employee")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		Employee[] employees = mapper.readValue(response, Employee[].class);
		assertNotNull(employees);
		assertEquals(1, employees.length);
		assertEquals("512916709388554621665590841409543270", employees[0].getId().toString());
	}
	
	@Test
	void getBatchSearchByid() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/employee/batch/search/byid").content("[]");
		String response = restApi.perform(request)
				.andReturn().getResponse().getContentAsString();
		Employee[] employees = mapper.readValue(response, Employee[].class);
		assertNotNull(employees);
		assertEquals(1, employees.length);
		assertEquals("512916709388554621665590841409543270", employees[0].getId().toString());
	}

}
