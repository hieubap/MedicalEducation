package medical.education.dao.repository;

import java.util.List;
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

  List<RegisterEntity> findByCourseId(Long courseId);

  List<RegisterEntity> findAllByCourseId(Long courseId);

  @Query(" select e from RegisterEntity e "
          + " where (e.studentId = :#{#studentId})"
          + " and ( e.courseInfo.status = 1 "
          + " or e.courseInfo.status = 2 ) ")
  RegisterEntity findCurrent(Long studentId);

  @Query(" select e from RegisterEntity e "
      + " where (e.courseId = :#{#courseId} or :#{#courseId} is null)"
      + " and ( e.status = medical.education.enums.RegisterEnum.REGISTER_DONED ) ")
  List<RegisterEntity> findRegistersToChangeSemester(Long courseId);

  @Query("select case when count(e) > 0 then true else false end "
      + " from RegisterEntity e"
      + " where (e.studentId = :#{#studentId} and e.status <> medical.education.enums.RegisterEnum.DONED)")
  boolean isStudying(Long studentId);

  Integer countByCourseId(Long id);
}
