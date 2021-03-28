package medical.education.dao.repository;

import medical.education.dao.model.CourseRegisterEntity;
import medical.education.dto.CourseRegisterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import spring.backend.library.dao.repository.BaseRepository;

public interface CourseRegisterRepository extends
    BaseRepository<CourseRegisterEntity, CourseRegisterDTO, Long> {

  @Override
  @Query(" select e from CourseRegisterEntity e "
      + " where (e.id = :#{#dto.id} or :#{#dto.id} is null)"
      + " and (e.courseId = :#{#dto.courseId} or :#{#dto.courseId} is null)"
      + " and (e.studentId = :#{#dto.studentId} or :#{#dto.studentId} is null)"
      + " and (e.status = :#{#dto.status} or :#{#dto.status} is null)")
  Page<CourseRegisterEntity> search(CourseRegisterDTO dto, Pageable pageable);
}
