package medical.education.dto;

import java.time.LocalDateTime;
import java.util.List;
import javax.security.auth.Subject;
import lombok.ToString;
import medical.education.enums.ClassStatusEnum;
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
  private Long placeId;
  private Long teacherId;

  private String code;
  private ClassStatusEnum status;
  private String time;
  private PlaceDTO place;
  private Long numberRegister;
  private Long limitRegister;

  private List<UserDTO> listRegister;
//  private CourseDTO course;
  private SubjectDTO subject;
  private UserDTO teacher;
}
