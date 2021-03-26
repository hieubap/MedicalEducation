package medical.education.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class CourseDTO extends BaseDTO {

  private String name;

  private String value;

  private String thoiGianHoc;

  private String details;

  private List<SubjectDTO> subjects;

  private List<Long> subjectIds;

  private List<UserDTO> registers;

  private Long price;
}
