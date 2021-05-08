package medical.education.dao.repository;

import java.util.List;
import medical.education.dao.model.CourseSubjectEntity;
import medical.education.dao.model.ScheduleEntity;
import medical.education.dto.ScheduleDTO;
import medical.education.enums.KipHocEnum;
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

  @Query("select case when count(e) > 0 then true else false end from ScheduleEntity e "
          + "where 1=1 "
          + "and (e.kipHoc = :kipHoc)"
          + "and (e.id <> :id or :id is null)"
          + "and (e.day = :day)"
          + "and (e.placeId = :placeId)")
  Boolean checkExistByDayAndKipHoc(KipHocEnum kipHoc, Short day, Long id, Long placeId);
}
