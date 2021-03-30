package co.com.ingeniods.humantalent.infrastructure.repository.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeMetadataDTO {
	private String email;
	private List<String> phoneNumber;
	private String address;
}
