package medical.education.dao.repository;

import medical.education.dao.model.RegisterEntity;
import medical.education.dto.RegisterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import spring.backend.library.dao.repository.BaseRepository;

public interface RegisterRepository extends
    BaseRepository<RegisterEntity, RegisterDTO, Long> {

  @Override
  @Query(" select e from RegisterEntity e "
      + " where (e.id = :#{#dto.id} or :#{#dto.id} is null)"
      + " and (e.courseId = :#{#dto.courseId} or :#{#dto.courseId} is null)"
      + " and (e.studentId = :#{#dto.studentId} or :#{#dto.studentId} is null)"
      + " and (e.status = :#{#dto.status} or :#{#dto.status} is null)")
  Page<RegisterEntity> search(RegisterDTO dto, Pageable pageable);

  RegisterEntity findByCourseIdAndStudentId(Long courseID,Long studentId);
}
