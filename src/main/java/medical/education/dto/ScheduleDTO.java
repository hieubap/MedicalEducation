package medical.education.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import medical.education.enums.KipHocEnum;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ScheduleDTO extends BaseDTO {

    private Short day;
    private Long placeId;
    private PlaceDTO place;
    private Long subjectId;
    private SubjectDTO subject;
    private Long courseId;
    private KipHocEnum kipHoc;
}
