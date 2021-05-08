package medical.education.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import medical.education.enums.CourseStatusEnum;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class CourseDTO extends BaseDTO {

  private Long id;

  private String name;

  private String code;

  private String nameHealthFacility;

  private String nameUserCreated;

  private Short status;

  @JsonProperty
  private Date ngayKhaiGiang;

  @JsonProperty
  private Date ngayKetThuc;

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

  private List<ScheduleDTO> listSchedules;

  private CourseStatusEnum courseStatusEnum;
}
