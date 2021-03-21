package co.com.ingeniods.humantalent.domain.service;

import co.com.ingeniods.humantalent.domain.model.Employee;

@FunctionalInterface
public interface FindAllEmployee {
  
  Iterable<Employee> execute();

}
