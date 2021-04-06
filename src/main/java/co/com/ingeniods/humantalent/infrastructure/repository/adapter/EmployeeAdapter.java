package co.com.ingeniods.humantalent.infrastructure.repository.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Row2;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import co.com.ingeniods.humantalent.application.service.EmployeeService;
import co.com.ingeniods.humantalent.application.service.arguments.EmployeeServiceArgs;
import co.com.ingeniods.humantalent.domain.service.ExistsEmployeeByPersonId;
import co.com.ingeniods.humantalent.domain.service.FindAllEmployee;
import co.com.ingeniods.humantalent.domain.service.FindEmployeeByPersonIds;
import co.com.ingeniods.humantalent.domain.service.SaveEmployee;
import co.com.ingeniods.humantalent.infrastructure.repository.assembler.EmployeeAssembler;
import co.com.ingeniods.humantalent.infrastructure.repository.port.EmployeeRepository;
import co.com.ingeniods.humantalent.infrestructure.repository.dto.Tables;
import co.com.ingeniods.humantalent.infrestructure.repository.dto.tables.pojos.EmployeePojo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeAdapter extends EmployeeService {

	public EmployeeAdapter(EmployeeRepository entityRepository, DSLContext dslContext) {
		super(EmployeeAdapter.getArguments(entityRepository, dslContext));
	}

	private static EmployeeServiceArgs getArguments(EmployeeRepository entityRepository, DSLContext dslContext) {
		log.debug("Creating EmployeeServiceArgs");
		return EmployeeServiceArgs.builder().existsByPersonId(existsByPersonId(entityRepository))
				.findAll(findAll(entityRepository)).save(saveClient(entityRepository))
				.findByPersonIds(findByPersonIds(dslContext)).build();
	}

	private static ExistsEmployeeByPersonId existsByPersonId(EmployeeRepository entityRepository) {
		return personId -> {
			return entityRepository.countByIdentification(personId.getType(), personId.getNumber()) > 0;
		};
	}

	private static FindAllEmployee findAll(EmployeeRepository entityRepository) {
		return () -> EmployeeAssembler.INSTANCE.toEntity(entityRepository.findAll());
	}

	private static SaveEmployee saveClient(EmployeeRepository entityRepository) {
		return entity -> entityRepository.save(EmployeeAssembler.INSTANCE.toDto(entity));
	}

	private static FindEmployeeByPersonIds findByPersonIds(DSLContext dslContext) {
		return ids -> {
			List<Row2<String, String>> idsRows = ids.stream().map(id -> DSL.row(id.getType().name(), id.getNumber()))
					.collect(Collectors.toList());
			Condition inClause = DSL.row(Tables.EMPLOYEE.ID_TYPE, Tables.EMPLOYEE.ID_NUMBER).in(idsRows);
			return dslContext.selectFrom(Tables.EMPLOYEE).where(inClause).fetchInto(EmployeePojo.class).stream()
					.map(EmployeeAssembler.INSTANCE::toEntity).collect(Collectors.toList());
		};
	}

}
