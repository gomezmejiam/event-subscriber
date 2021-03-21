package co.com.ingeniods.humantalent.domain.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactInformation {
	private String email;
	private List<String> phoneNumber;
	private String address;
}
