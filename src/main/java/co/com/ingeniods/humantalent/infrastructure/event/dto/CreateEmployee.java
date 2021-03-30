package co.com.ingeniods.humantalent.infrastructure.event.dto;

import co.com.ingeniods.humantalent.domain.model.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEmployee {
  
  private Employee employee;

}
