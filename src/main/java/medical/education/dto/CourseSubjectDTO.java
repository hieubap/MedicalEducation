package medical.education.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class CourseSubjectDTO extends BaseDTO {
  private Long courseId;
  private Long subjectId;
  private SubjectDTO subject;
}
