package co.com.ingeniods.shared.exception.domain;

import co.com.ingeniods.shared.exception.base.BusinessException;
import co.com.ingeniods.shared.exception.base.ExceptionCode;
import co.com.ingeniods.shared.modules.domain.Entity;

public class EntityPropertyValueException extends BusinessException {

	private static final long serialVersionUID = 110688007013370820L;
	
	public EntityPropertyValueException(Class<? extends Entity> clazz, String propertyName, Object value) {
		super(ExceptionCode.REQUIRED_ENTITY_PROPERTY, getMessage(clazz, propertyName, value));
	}

	private static String getMessage(Class<? extends Entity> clazz, String propertyName, Object value) {
		return String.format("En la clase %s el valor de %s no puede ser %s", clazz.getSimpleName(), propertyName, String.valueOf(value));
	}

}
