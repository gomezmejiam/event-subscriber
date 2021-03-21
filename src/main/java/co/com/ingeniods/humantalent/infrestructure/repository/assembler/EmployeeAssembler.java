package co.com.ingeniods.humantalent.infrestructure.repository.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import co.com.ingeniods.humantalent.domain.model.Employee;
import co.com.ingeniods.humantalent.infrestructure.repository.dto.EmployeeDTO;
import co.com.ingeniods.shared.infrastructure.repository.assembler.DtoAssembler;

@Mapper
public interface EmployeeAssembler extends DtoAssembler<Employee, EmployeeDTO> {

	EmployeeAssembler INSTANCE = Mappers.getMapper(EmployeeAssembler.class);

	@Mapping(source = "person.id.type", target = "idType")
	@Mapping(source = "person.id.number", target = "idNumber")
	@Mapping(source = "person.name.firtsName", target = "firtsName")
	@Mapping(source = "person.name.middleName", target = "middleName")
	@Mapping(source = "person.name.surname", target = "surname")
	@Mapping(source = "person.name.secondSurname", target = "secondSurname")
	//@Mapping(target = "additional", source = "contact")
	EmployeeDTO toDto(Employee entity);

	@Mapping(target = "person.id.type", source = "idType")
	@Mapping(target = "person.id.number", source = "idNumber")
	@Mapping(target = "person.name.firtsName", source = "firtsName")
	@Mapping(target = "person.name.middleName", source = "middleName")
	@Mapping(target = "person.name.surname", source = "surname")
	@Mapping(target = "person.name.secondSurname", source = "secondSurname")
	//@Mapping(target = "contact", source = "additional")
	Employee toEntity(EmployeeDTO dto);

}
