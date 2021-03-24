package co.com.ingeniods.humantalent.infrestructure.api;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.ingeniods.humantalent.application.service.EmployeeService;
import co.com.ingeniods.humantalent.domain.model.Employee;
import co.com.ingeniods.humantalent.domain.model.PersonId;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeService domainService;
	
	@PostMapping("batch/search/byid")
	public Iterable<Employee> findByIds(@RequestBody List<PersonId> ids){
		if(Objects.isNull(ids) || ids.isEmpty()) {
			return null;
		}
		return domainService.findByPersonIds(ids);
	}
	
	@GetMapping
	public Iterable<Employee> findAll(){
		return domainService.findAll();
	}

}
