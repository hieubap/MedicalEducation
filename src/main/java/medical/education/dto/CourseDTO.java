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

  private Long id;

  private String name;

  private String code;

  private String startTime;

  private String endTime;

  private Long price;

  private Integer numberLesson;

  private Integer numberRegister;

  private Integer limitRegister;

  private Long healthFacilityId;

  private Object healthFacility;

  private String subjectIds;

  private List<SubjectDTO> listSubject;

  private List<UserDTO> listRegisters;

  private Object userCreated;
}
