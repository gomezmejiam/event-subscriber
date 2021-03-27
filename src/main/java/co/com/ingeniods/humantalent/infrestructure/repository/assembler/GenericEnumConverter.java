package co.com.ingeniods.humantalent.infrestructure.repository.assembler;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenericEnumConverter<E extends Enum<E>> implements AttributeConverter<E, String> {

	private final Class<E> valueClass;
	
	public GenericEnumConverter(Class<E> valueClass) {
		this.valueClass = valueClass;
	}
	
	@Override
	public String convertToDatabaseColumn(E type) {
		if (type == null) {
			return null;
		}
		return type.name();
	}

	@Override
	public E convertToEntityAttribute(String code) {
		if (code == null) {
			return null;
		}

		return Stream.of(valueClass.getEnumConstants()).filter(c -> c.name().equals(code)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}
