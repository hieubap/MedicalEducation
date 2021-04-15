package medical.education.research.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class StudentDTO extends BaseDTO {

  private Long id;

  private Boolean active;

  private String name;

  private String telecom;

  private AddressDTO address;

  private Short gender;

  private LocalDate birthDate;

  private String photo;

  private String communication;
}
