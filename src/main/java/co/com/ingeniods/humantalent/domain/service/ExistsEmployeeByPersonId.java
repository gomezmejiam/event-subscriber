package co.com.ingeniods.humantalent.domain.service;

import co.com.ingeniods.humantalent.domain.model.PersonId;

@FunctionalInterface
public interface ExistsEmployeeByPersonId {
  
  boolean execute(PersonId personId);

}
