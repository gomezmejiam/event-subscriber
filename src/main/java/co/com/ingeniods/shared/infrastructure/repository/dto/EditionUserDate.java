package co.com.ingeniods.shared.infrastructure.repository.dto;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@Getter
@Embeddable
public class EditionUserDate implements Serializable {

	private static final long serialVersionUID = 9008754632691538374L;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date", columnDefinition = "DATE")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate modifiedDate;
	
	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@Column(name = "created_date", updatable = false, columnDefinition = "DATE")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate createdDate;
}
