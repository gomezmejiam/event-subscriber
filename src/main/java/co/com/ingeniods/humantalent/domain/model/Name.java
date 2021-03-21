package co.com.ingeniods.humantalent.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Name {
  private String firtsName;
  private String middleName;
  private String surname;
  private String secondSurname;

}