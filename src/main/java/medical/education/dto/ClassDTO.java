package medical.education.dto;

import java.util.List;
import lombok.ToString;
import spring.backend.library.dto.BaseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ClassDTO extends BaseDTO {
  private Long subjectId;
  private List<UserDTO> register;
}
