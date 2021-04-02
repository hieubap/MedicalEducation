package medical.education.dto;

import java.time.LocalDateTime;
import java.util.List;
import javax.security.auth.Subject;
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
  private Long courseId;
  private Long teacherId;

  private List<UserDTO> listRegister;
  private CourseDTO course;
  private SubjectDTO subject;
  private String code;
  private Short status;
  private String time;
//  private LocalDateTime startTime;
//  private LocalDateTime endTime;
  private Long numberRegister;
}
