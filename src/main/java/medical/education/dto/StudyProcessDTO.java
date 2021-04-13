package medical.education.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import medical.education.enums.StudyProcessEnum;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class StudyProcessDTO extends BaseDTO {
  private Long studentId;

  private SubjectDTO subject;
  /**
   * lớp học
   */
  private Long classId;

  private StudyProcessEnum status;

  private String muster;

  private Double midPoint;

  private Double endPoint;

  private Double total;
}
