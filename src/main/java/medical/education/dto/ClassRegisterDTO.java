package medical.education.dto;

import lombok.ToString;
import medical.education.research.dto.StudentDTO;
import spring.backend.library.dto.BaseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ClassRegisterDTO extends BaseDTO {
  private Long studentId;
  private Long classId;
  private UserDTO student;
}
