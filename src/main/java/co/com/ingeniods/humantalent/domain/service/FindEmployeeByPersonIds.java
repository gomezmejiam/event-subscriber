package co.com.ingeniods.humantalent.domain.service;

import java.util.List;

import co.com.ingeniods.humantalent.domain.model.Employee;
import co.com.ingeniods.humantalent.domain.model.PersonId;

@FunctionalInterface
public interface FindEmployeeByPersonIds {
  
  Iterable<Employee> execute(List<PersonId> personIds);

}
