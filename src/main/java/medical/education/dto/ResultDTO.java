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

  private UserDTO studentInfo;

  private Long registerId;

  private RegisterDTO registerInfo;

  private Long subjectId;

  private SubjectDTO subjectInfo;

  private Integer muster;

  private Double midPoint;

  private Double endPoint;

  private Double total;

  private String rank;

  private Short isPass;

}
