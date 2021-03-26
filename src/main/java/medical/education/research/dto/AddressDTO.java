package medical.education.research.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO extends BaseDTO {
  private String name;
  private Long streetId;
  private Long cityId;
  private Long provincialId;
}
