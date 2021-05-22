package medical.education.dao.repository;

import java.util.List;
import medical.education.dao.model.ClassEntity;
import medical.education.dto.ClassDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends CrudRepository<ClassEntity, Long> {

  @Query(value = "select u.id             as id,"
      + "             rl.id             as result_id,"
      + "             u.full_name      as name,"
      + "             u.current_course_id as class_id,"
      + "             rl.mid_point     as mid_point,"
      + "             rl.end_point     as end_point,"
      + "             rl.attendance    as muster,"
      + "             rl.total         as total,"
      + "             rl.subject_id    as subject_id,"
      + "             sj.lesson        as lesson"
      + "      from users u"
      + "           join results rl on u.id = rl.student_id"
      + "           join course cr on cr.id = u.current_course_id"
      + "           join subject sj on sj.id = rl.subject_id"
      + "           join register rg on rg.id = rl.register_id"
      + " where (cr.semester = rg.semester)"
      + " and (rg.id = rl.register_id)"
      + " and (rl.course_id = :#{#dto.courseId} and :#{#dto.courseId} <> -1)"
      + " and (rl.subject_id = :#{#dto.subjectId} and :#{#dto.subjectId} <> -1)"
      + " and (lower(u.full_name) like :#{#dto.name} or :#{#dto.name} = '')"
      ,nativeQuery = true)
  Page<ClassEntity> search(ClassDTO dto, Pageable pageable);

//    @Query(value = "select u.id as id,"
//        + "      u.fullName as name,"
//        + "      u.currentCourseId as classId,"
//        + "      r.midPoint as midPoint,"
//        + " r.endPoint as endPoint,"
//        + " r.total as total,"
//        + " r.muster as muster,"
//        + " r.subjec"
//        + " from UserEntity u"
//        + " join ResultEntity r on r.studentId = u.id"
//        + " ")
//  Page<ClassEntity> search(ClassDTO dto, Pageable pageable);
}
