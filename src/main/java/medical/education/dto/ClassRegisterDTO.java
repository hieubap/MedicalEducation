package medical.education.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import medical.education.enums.ClassRegisterEnum;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ClassRegisterDTO extends BaseDTO {

  private Long studentId;

  private Long classId;

  private UserDTO student;

  private String courseName;

  private ClassDTO classInfo;

  private String codeClass;

  private ClassRegisterEnum status;
}
