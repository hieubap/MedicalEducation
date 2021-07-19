package medical.education.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import medical.education.dao.model.UserEntity;
import medical.education.enums.RegisterEnum;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class RegisterDTO extends BaseDTO {

  private Long studentId;

  private Long courseId;

  private UserDTO studentInfo;

  private CourseDTO courseInfo;

  private RegisterEnum status;

  private Double total;

  private String kind;
}
