package medical.education.dao.repository;

import java.util.List;
import medical.education.dao.model.CourseSubjectEntity;
import medical.education.dao.model.ScheduleEntity;
import medical.education.dto.ScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import spring.backend.library.dao.repository.BaseRepository;

public interface ScheduleRepository extends BaseRepository<ScheduleEntity, ScheduleDTO,Long> {
  @Query("select case when count(e) > 0 then true else false end from CourseSubjectEntity e where "
      + " e.courseId = :#{#courseId} and e.subjectId = :#{#subjectId} ")
  boolean exist(Long courseId,Long subjectId);

  List<ScheduleEntity> findByCourseId(Long courseId);

  @Override
  @Query("select e from ScheduleEntity e "
      + " where (e.id = :#{#dto.id} or :#{#dto.id} is null) "
      + " and (e.courseId = :#{#dto.courseId} or :#{#dto.courseId} is null) ")
  Page<ScheduleEntity> search(ScheduleDTO dto, Pageable pageable);
}
