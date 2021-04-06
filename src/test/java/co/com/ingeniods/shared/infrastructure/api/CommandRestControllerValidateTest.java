package co.com.ingeniods.shared.infrastructure.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

import co.com.ingeniods.config.PersistenceConfig;
import co.com.ingeniods.config.jackson.JacksonConfiguration;
import co.com.ingeniods.humantalent.application.service.EmployeeValidator;
import co.com.ingeniods.humantalent.domain.model.Employee;
import co.com.ingeniods.humantalent.infrastructure.event.listener.CreateEmployeeListener;
import co.com.ingeniods.humantalent.infrastructure.repository.adapter.EmployeeAdapter;
import co.com.ingeniods.humantalent.infrastructure.repository.assembler.EmployeeAssembler;
import co.com.ingeniods.humantalent.infrastructure.repository.dto.EmployeeDTO;
import co.com.ingeniods.humantalent.infrastructure.repository.port.EmployeeRepository;
import co.com.ingeniods.shared.infrastructure.api.resource.EventResource;

@AutoConfigureMockMvc(addFilters = true)
@ContextConfiguration(classes = { PersistenceConfig.class, DSLContext.class, JacksonConfiguration.class,
		CreateEmployeeListener.class, EmployeeAdapter.class, EmployeeValidator.class, CommandRestController.class })
@WebMvcTest(includeFilters = @Filter(classes = { Repository.class }))
@ActiveProfiles("test")
class CommandRestControllerValidateTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private CreateEmployeeListener createEmployeeListener;

	@MockBean
	private DSLContext dslContext;

	@Autowired
	private MockMvc restApi;

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
	
	private String getEventResource(String eventType, String idNumber) {
		EventResource eventResource = new EventResource();
		eventResource.setData(
				"{\"employee\":{\"id\":1,\"person\":{\"id\":{\"type\":\"CC\",\"number\":\""+idNumber+"\"},\"name\":{\"firtsName\":\"A\",\"middleName\":\"B\",\"surname\":\"C\",\"secondSurname\":\"D\"},\"birthDate\":null}}}");
		eventResource.setId(UUID.randomUUID().toString());
		eventResource.setHeaders(new HashMap<>());
		eventResource.getHeaders().put("EVENT_TYPE", eventType);
		Gson gson = new Gson();
		return gson.toJson(eventResource);
	}
	
	private String getEventResource(String idNumber) {
		return getEventResource("CREATE_EMPLOYEE", idNumber);
	}


	@Test
	void validateWithError() throws Exception {
		List<EmployeeDTO> result = new ArrayList<EmployeeDTO>();
		employeeRepository.findAll().forEach(result::add);
		int initialSize = result.size();

		String eventRequest = getEventResource("1");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/command/validate/").content(eventRequest)
				.contentType(MediaType.APPLICATION_JSON);
		String response = restApi.perform(request).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		
		List<EmployeeDTO> resultPost = new ArrayList<EmployeeDTO>();
		employeeRepository.findAll().forEach(resultPost::add);
		int finalSize = resultPost.size();
		
		assertEquals("false", response);
		assertEquals(initialSize,finalSize);
	}

	@Test
	void validateWithoutError() throws Exception {
		String eventRequest = getEventResource("123");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/command/validate/").content(eventRequest)
				.contentType(MediaType.APPLICATION_JSON);
		String response = restApi.perform(request).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		assertEquals("true", response);
	}
	
	@Test
	void validateWithoutProcessor() throws Exception {
		String eventRequest = getEventResource("NO_PROCESSOR","2");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/command/validate/").content(eventRequest)
				.contentType(MediaType.APPLICATION_JSON);
		String response = restApi.perform(request).andExpect(status().isNotAcceptable()).andReturn().getResponse()
				.getContentAsString();
		assertEquals("NO_PROCESSOR_FOUND", response);
	}

	
}
