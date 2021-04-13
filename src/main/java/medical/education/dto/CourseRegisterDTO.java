package medical.education.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import medical.education.enums.CourseRegisterEnum;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class CourseRegisterDTO extends BaseDTO {

  private Long studentId;

  private Long courseId;

  private String code;

  private UserDTO student;

  private CourseDTO course;

  private CourseRegisterEnum status;
}
