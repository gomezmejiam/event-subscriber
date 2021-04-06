package co.com.ingeniods.shared.modules.domain;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;

import co.com.ingeniods.shared.exception.domain.EntityPropertyValueException;
import lombok.Getter;

@Getter
public abstract class Entity {
	
	private final BigInteger id;
	
	protected Entity() {
		id = generateId();
	}
	
	protected Entity(BigInteger id) {
		this.id = Objects.isNull(id)?generateId():id;
		validate();
	}
	
	private void validate() {
		if(Objects.isNull(id) || id.doubleValue() < 1) {
			throw new EntityPropertyValueException(this.getClass(), "id", id);
		}
	}
	
	private BigInteger generateId() {
		UUID myuuid = UUID.randomUUID();
		long highbits = Math.abs(myuuid.getMostSignificantBits());
		long lowbits =  Math.abs(myuuid.getLeastSignificantBits());
		byte[] bytes = ByteBuffer.allocate(16).putLong(highbits).putLong(lowbits).array();
		return new BigInteger(bytes);
	}

}
