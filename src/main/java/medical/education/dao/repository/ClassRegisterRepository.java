package medical.education.dao.repository;

import java.util.List;
import medical.education.dao.model.ClassRegisterEntity;
import medical.education.dto.ClassRegisterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import spring.backend.library.dao.repository.BaseRepository;

public interface ClassRegisterRepository extends
    BaseRepository<ClassRegisterEntity, ClassRegisterDTO, Long> {

  @Override
  @Query("select e from ClassRegisterEntity e"
      + " where (e.studentId = :#{#dto.studentId} or :#{#dto.studentId} is null )"
      + " and (e.classId = :#{#dto.classId} or :#{#dto.classId} is null )")
  Page<ClassRegisterEntity> search(ClassRegisterDTO dto, Pageable pageable);

  Long countByClassId(Long id);

  List<ClassRegisterEntity> findByClassId(Long id);

  boolean existsByClassIdAndStudentId(Long classId,Long studentId);
}
