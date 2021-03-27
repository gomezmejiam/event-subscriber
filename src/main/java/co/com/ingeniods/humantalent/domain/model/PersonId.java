package co.com.ingeniods.humantalent.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonId {
  private DocumentType type;
  private String number;
  
  public String toString() {
	  return type.getValue()+number;
  }

}