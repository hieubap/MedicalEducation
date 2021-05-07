package medical.education.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import medical.education.enums.GenderEnum;
import medical.education.enums.RoleEnum;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class UserDTO extends BaseDTO {
  private String avatar;

  private String username;

  private String password;

  private String passwordChange;

  private String fullName;

  private String value;

  private Short status;

  private Long age;

  private GenderEnum gender;

  private String address;

  private String email;

  private String phoneNumber;

  private Long idChange;

  private RoleEnum role;

  private RoleDTO roleDTO;

  private List<ResultDTO> listStudyProcess;

  private Long currentCourseId;

  private CourseDTO currentCourse;
}
