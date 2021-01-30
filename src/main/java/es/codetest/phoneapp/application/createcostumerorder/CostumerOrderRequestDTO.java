package es.codetest.phoneapp.application.createcostumerorder;

import java.util.List;
import java.util.StringJoiner;

public class CostumerOrderRequestDTO {

  private String name;
  private String surname;
  private String email;
  private List<String> phonesNames;

  public CostumerOrderRequestDTO(String name, String surname, String email, List<String> phonesNames) {
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.phonesNames = phonesNames;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String getEmail() {
    return email;
  }

  public List<String> getPhonesNames() {
    return phonesNames;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", CostumerOrderRequestDTO.class.getSimpleName() + "[", "]")
        .add("name='" + name + "'")
        .add("surname='" + surname + "'")
        .add("email='" + email + "'")
        .add("phonesNames=" + phonesNames)
        .toString();
  }
}
