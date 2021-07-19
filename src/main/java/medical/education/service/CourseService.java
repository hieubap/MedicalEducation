package medical.education.service;

import medical.education.dto.PointsDTO;
import spring.backend.library.service.BaseService;
import medical.education.dto.CourseDTO;

public interface CourseService extends BaseService<CourseDTO> {
    PointsDTO listPointBySubject(Long courseId,Long subjectId);
}
