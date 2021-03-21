package co.com.ingeniods.humantalent.infrestructure.repository.port;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.com.ingeniods.humantalent.infrestructure.repository.dto.EmployeeDTO;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeDTO, BigInteger> {

	@Query("SELECT c FROM EmployeeDTO c WHERE c.idType = ?1 and c.idNumber = ?2")
	boolean findByIdentification(String type, String number);

}
