package medical.education.dao.repository;

import medical.education.dao.model.CourseRegisterEntity;
import medical.education.dto.CourseRegisterDTO;
import spring.backend.library.dao.repository.BaseRepository;

public interface CourseRegisterRepository extends
    BaseRepository<CourseRegisterEntity, CourseRegisterDTO, Long> {

}
