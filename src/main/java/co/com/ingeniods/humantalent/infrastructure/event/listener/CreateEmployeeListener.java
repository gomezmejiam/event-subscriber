package co.com.ingeniods.humantalent.infrastructure.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ingeniods.humantalent.application.service.EmployeeService;
import co.com.ingeniods.humantalent.application.service.EmployeeValidator;
import co.com.ingeniods.humantalent.domain.model.Employee;
import co.com.ingeniods.humantalent.infrastructure.event.dto.CreateEmployee;
import co.com.ingeniods.shared.event.EventProcessor;
import co.com.ingeniods.shared.event.domain.Event;
import co.com.ingeniods.shared.exception.events.UnreadableEventException;

@Component
public class CreateEmployeeListener extends EventProcessor<Employee> {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private EmployeeService clientService;

	public CreateEmployeeListener(EmployeeValidator validator) {
		super("CREATE_EMPLOYEE", validator);
	}

	@Override
	public Employee readValue(String content) {
		try {
			return mapper.readValue(String.valueOf(content), CreateEmployee.class).getEmployee();
		} catch (JsonProcessingException e) {
			throw new UnreadableEventException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void process(Event<Employee> entityEvent) {
		clientService.save(entityEvent.getData());
	}

}
