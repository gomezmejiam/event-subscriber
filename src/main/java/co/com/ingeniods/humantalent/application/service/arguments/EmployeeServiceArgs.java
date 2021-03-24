package co.com.ingeniods.humantalent.application.service.arguments;


import co.com.ingeniods.humantalent.domain.service.ExistsEmployeeByPersonId;
import co.com.ingeniods.humantalent.domain.service.FindAllEmployee;
import co.com.ingeniods.humantalent.domain.service.FindEmployeeByPersonIds;
import co.com.ingeniods.humantalent.domain.service.SaveEmployee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Getter
@Builder
@Value
@AllArgsConstructor
public class EmployeeServiceArgs {
	private final ExistsEmployeeByPersonId existsByPersonId;
	private final FindAllEmployee findAll;
	private final SaveEmployee save;
	private final FindEmployeeByPersonIds findByPersonIds;
}
