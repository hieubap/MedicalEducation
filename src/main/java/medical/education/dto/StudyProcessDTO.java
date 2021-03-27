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
public class StudyProcessDTO extends BaseDTO {
  private Long studentId;

  /**
   * lớp học
   */
  private Long classId;

  private Short status;

  private String muster;

  private Double midPoint;

  private Double endPoint;

  private Double total;
}
