package co.com.ingeniods.humantalent.infrestructure.repository.adapter;

import org.springframework.stereotype.Service;

import co.com.ingeniods.humantalent.application.service.EmployeeService;
import co.com.ingeniods.humantalent.application.service.arguments.EmployeeServiceArgs;
import co.com.ingeniods.humantalent.domain.service.ExistsEmployeeByPersonId;
import co.com.ingeniods.humantalent.domain.service.FindAllEmployee;
import co.com.ingeniods.humantalent.domain.service.SaveEmployee;
import co.com.ingeniods.humantalent.infrestructure.repository.assembler.EmployeeAssemblerImpl;
import co.com.ingeniods.humantalent.infrestructure.repository.port.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeAdapter extends EmployeeService {

	public EmployeeAdapter(EmployeeRepository entityRepository) {
		super(EmployeeAdapter.getArguments(entityRepository));
	}

	private static EmployeeServiceArgs getArguments(EmployeeRepository entityRepository) {
		log.debug("Creating EmployeeServiceArgs");
		return EmployeeServiceArgs.builder().existsByPersonId(existsByPersonId(entityRepository))
				.findAll(findAll(entityRepository)).save(saveClient(entityRepository)).build();
	}

	private static ExistsEmployeeByPersonId existsByPersonId(EmployeeRepository entityRepository) {
		return (personId) -> entityRepository.findByIdentification(personId.getType().getValue(), personId.getNumber());
	}

	private static FindAllEmployee findAll(EmployeeRepository entityRepository) {
		return () -> EmployeeAssemblerImpl.INSTANCE.toEntity(entityRepository.findAll());
	}

	private static SaveEmployee saveClient(EmployeeRepository entityRepository) {
		return (entity) -> entityRepository.save(EmployeeAssemblerImpl.INSTANCE.toDto(entity));
	}

}
