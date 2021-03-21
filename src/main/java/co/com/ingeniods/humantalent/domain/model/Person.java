package co.com.ingeniods.humantalent.domain.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
  private PersonId id;
  private Name name;
  private LocalDate birthDate;

}