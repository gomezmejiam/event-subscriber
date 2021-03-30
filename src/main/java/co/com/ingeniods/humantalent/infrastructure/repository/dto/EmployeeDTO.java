package co.com.ingeniods.humantalent.infrastructure.repository.dto;

import javax.persistence.Convert;
import javax.persistence.Entity;

import co.com.ingeniods.humantalent.domain.model.DocumentType;
import co.com.ingeniods.humantalent.infrastructure.repository.assembler.DocumentTypeConverter;
import co.com.ingeniods.shared.infrastructure.repository.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EmployeeDTO extends BaseDto {

	private static final long serialVersionUID = -5097644618759200637L;

	@Convert(converter = DocumentTypeConverter.class)
	private DocumentType idType;
	private String idNumber;
	private String firtsName;
	private String middleName;
	private String surname;
	private String secondSurname;
}
