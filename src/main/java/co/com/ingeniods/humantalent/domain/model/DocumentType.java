package co.com.ingeniods.humantalent.domain.model;

import java.util.Objects;
import java.util.Optional;

import co.com.ingeniods.shared.modules.domain.DomainEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DocumentType implements DomainEnum<String> {

	CEDULA_CIUDADANIA("CC", "Cédula de Ciudadanía"), 
	TARJETA_IDENTIDAD("TI", "Tarjeta de Identidad");

	private final String value;
	private final String description;

	public boolean isEnum(String value) {
		if (Objects.isNull(value) || Optional.ofNullable(value).orElse("").trim().isEmpty()) {
			return false;
		}
		return this.value.equalsIgnoreCase(value);
	}

}
