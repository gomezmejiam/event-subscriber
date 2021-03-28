package co.com.ingeniods.humantalent.domain.model;

import java.math.BigInteger;

import co.com.ingeniods.shared.modules.domain.Entity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Employee extends Entity {
	private Person person;

	@Builder(builderMethodName = "employeeBuilder")
	public Employee(BigInteger id, Person person) {
		super(id);
		this.person = person;
	}
	
	
}
