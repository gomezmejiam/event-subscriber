package co.com.ingeniods.humantalent.application.service;

import java.util.Objects;

import co.com.ingeniods.humantalent.application.service.arguments.EmployeeServiceArgs;
import co.com.ingeniods.humantalent.domain.model.Employee;
import co.com.ingeniods.humantalent.domain.model.PersonId;

public abstract class EmployeeService {
	
	private final EmployeeServiceArgs arguments;
	public EmployeeService(EmployeeServiceArgs arguments) {
		this.arguments = Objects.requireNonNull(arguments, "El objeto de acceso a servicios no puede ser null");
	}

	public void save(Employee entity) {
		arguments.getSave().execute(entity);
	}

	public Iterable<Employee> findAll(){
		return arguments.getFindAll().execute();
	}
	
	public boolean existsByPersonId(PersonId personId) {
		return arguments.getExistsByPersonId().execute(personId);
	}
	

}
