package co.com.ingeniods.shared.infrastructure.repository.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseDto implements Serializable {

	private static final long serialVersionUID = -7478940109106695881L;

	@Id
	@Basic(optional = false)
	@Column(name = "_id", unique = true, precision = 40, scale = 0)
	private BigInteger id;

	@Embedded
	private EditionUserDate editionUserDate;

	@PrePersist
	public void onPrePersist() {
		editionUserDate = new EditionUserDate();
		editionUserDate.setCreatedBy("USER");
		editionUserDate.setModifiedBy("USER");
		editionUserDate.setCreatedDate(LocalDate.now());
		editionUserDate.setModifiedDate(LocalDate.now());
	}

	@PreUpdate
	public void onPreUpdate() {
		editionUserDate.setModifiedBy("USER");
		editionUserDate.setModifiedDate(LocalDate.now());
	}

}
