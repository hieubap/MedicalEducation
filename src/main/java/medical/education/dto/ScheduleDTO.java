package medical.education.dto;

import java.time.LocalDateTime;
import lombok.ToString;
import medical.education.dao.model.CourseEntity;
import spring.backend.library.dto.BaseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ScheduleDTO extends BaseDTO {
  private Short day;
  private String startTime;
  private String endTime;
  private Long placeId;
  private PlaceDTO place;
  private Long subjectId;
  private SubjectDTO subject;
  private Long courseId;
}
