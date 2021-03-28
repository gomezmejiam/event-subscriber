package co.com.ingeniods.humantalent.infrestructure.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.util.Collections;

import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ingeniods.humantalent.domain.model.DocumentType;
import co.com.ingeniods.humantalent.domain.model.Employee;
import co.com.ingeniods.humantalent.infrestructure.repository.adapter.EmployeeAdapter;
import co.com.ingeniods.humantalent.infrestructure.repository.dto.EmployeeDTO;
import co.com.ingeniods.humantalent.infrestructure.repository.port.EmployeeRepository;

@AutoConfigureMockMvc(addFilters = true)
@ContextConfiguration(classes = { EmployeeAdapter.class, EmployeeController.class })
@WebMvcTest(includeFilters = @Filter(classes = { Repository.class }))
class EmployeeControllerTest {

	@MockBean
	private EmployeeRepository employeeRepository;

	@MockBean
	private DSLContext dslContext;

	@Autowired
	private MockMvc restApi;
	
	@Autowired
	private ObjectMapper mapper;

	@BeforeEach
	void init() {
		EmployeeDTO mock = new EmployeeDTO();
		mock.setFirtsName("A");
		mock.setIdNumber("1");
		mock.setIdType(DocumentType.CEDULA_CIUDADANIA);
		mock.setMiddleName("B");
		mock.setSecondSurname("D");
		mock.setSurname("C");
		mock.setId(new BigInteger("512916709388554621665590841409543273"));
		when(employeeRepository.findAll()).thenReturn(Collections.singletonList(mock));
	}

	@Test
	void getEmployee() throws Exception {
		String response = restApi.perform(MockMvcRequestBuilders.get("/employee")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		Employee[] employees = mapper.readValue(response, Employee[].class);
		
		assertNotNull(employees);
		assertEquals(1, employees.length);
		assertEquals("512916709388554621665590841409543273", employees[0].getId().toString());
		
	}

}
