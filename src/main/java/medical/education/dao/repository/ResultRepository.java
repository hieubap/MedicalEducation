package medical.education.dao.repository;

import medical.education.dao.model.ResultEntity;
import medical.education.dto.ResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.backend.library.dao.repository.BaseRepository;

public interface ResultRepository extends BaseRepository<ResultEntity, ResultDTO,Long> {

  @Override
  @Query(" select e from ResultEntity e where "
      + " (e.studentId = :#{#dto.studentId} or :#{#dto.studentId} is null)"
      + " and (e.courseId = :#{#dto.courseId} or :#{#dto.courseId} is null)")
  Page<ResultEntity> search(ResultDTO dto, Pageable pageable);
}
