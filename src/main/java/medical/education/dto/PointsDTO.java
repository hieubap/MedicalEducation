package medical.education.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PointsDTO {
    CourseDTO courseInfo;

    SubjectDTO subjectInfo;

    List<RegisterDTO> listRegister;

    List<UserDTO> listStudent;

    List<ResultDTO> listResult;
}
