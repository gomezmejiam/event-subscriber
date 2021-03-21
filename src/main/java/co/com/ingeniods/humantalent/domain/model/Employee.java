package co.com.ingeniods.humantalent.domain.model;

import co.com.ingeniods.shared.modules.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee extends Entity {
	private Person person;
	//private ContactInformation contact;
}
