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
public class ResultDTO extends BaseDTO {

  private Long studentId;

//  private Object student;
  /**
   * lớp học
   */
  private Long courseId;

//  private Object course;

  private Long subjectId;

//  private Object subject;

  private Integer muster;

  private Double midPoint;

  private Double endPoint;

  private Double total;
}
