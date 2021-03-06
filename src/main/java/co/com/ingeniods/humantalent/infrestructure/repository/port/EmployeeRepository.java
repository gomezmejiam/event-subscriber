package co.com.ingeniods.humantalent.infrestructure.repository.port;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.ingeniods.humantalent.infrestructure.repository.dto.EmployeeDTO;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeDTO, BigInteger> {

	@Query("SELECT e FROM EmployeeDTO e WHERE e.idType = ?1 and e.idNumber = ?2")
	boolean findByIdentification(String type, String number);
	
	@Query("SELECT e FROM EmployeeDTO e WHERE (e.idType, e.idNumber) IN (:ids)")
	Iterable<EmployeeDTO> findByIdentificationList(@Param("ids") List<Object[]> ids);

}
