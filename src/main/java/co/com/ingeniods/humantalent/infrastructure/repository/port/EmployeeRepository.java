package co.com.ingeniods.humantalent.infrastructure.repository.port;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.com.ingeniods.humantalent.domain.model.DocumentType;
import co.com.ingeniods.humantalent.infrastructure.repository.dto.EmployeeDTO;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeDTO, BigInteger> {

	@Query("SELECT count(1) FROM EmployeeDTO e WHERE e.idType = ?1 and e.idNumber = ?2")
	Long countByIdentification(DocumentType type, String number);
	
	EmployeeDTO findByIdTypeAndIdNumber(DocumentType type, String number);
	
}
