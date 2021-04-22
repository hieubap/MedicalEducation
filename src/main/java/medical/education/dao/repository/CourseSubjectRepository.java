package medical.education.dao.repository;

import java.util.List;
import medical.education.dao.model.CourseSubjectEntity;
import medical.education.dto.CourseSubjectDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.library.dao.repository.BaseRepository;

public interface CourseSubjectRepository extends BaseRepository<CourseSubjectEntity, CourseSubjectDTO,Long> {

  @Query("select case when count(e) > 0 then true else false end from CourseSubjectEntity e where "
      + " e.courseId = :#{#courseId} and e.subjectId = :#{#subjectId} ")
  boolean exist(Long courseId,Long subjectId);

  List<CourseSubjectEntity> findByCourseId(Long courseId);

  @Transactional
  void deleteByCourseIdAndSubjectId(Long courseId,Long subjectId);
}
