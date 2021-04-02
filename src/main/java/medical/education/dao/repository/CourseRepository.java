package medical.education.dao.repository;

import medical.education.dao.model.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import spring.backend.library.dao.repository.BaseRepository;
import medical.education.dto.CourseDTO;

public interface CourseRepository extends BaseRepository<CourseEntity, CourseDTO,Long> {
  @Override
  @Query("select e from CourseEntity e "
      + " where (lower(e.name) like :#{#dto.name} or :#{#dto.name} is null) "
      + " and (e.id = :#{#dto.id} or :#{#dto.id} is null) ")
  Page<CourseEntity> search(CourseDTO dto, Pageable pageable);

  boolean existsByCode(String code);
}
