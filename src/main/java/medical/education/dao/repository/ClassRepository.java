package medical.education.dao.repository;

import medical.education.dao.model.ClassEntity;
import medical.education.dto.ClassDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import spring.backend.library.dao.repository.BaseRepository;

public interface ClassRepository extends BaseRepository<ClassEntity, ClassDTO,Long> {

  @Override
  @Query(" select e from ClassEntity e "
      + " where (e.id = :#{#dto.id} or :#{#dto.id} is null)"
      + " and (e.subjectId = :#{#dto.subjectId} or :#{#dto.subjectId} is null)"
      + " and (e.code = :#{#dto.code} or :#{#dto.code} is null)")
  Page<ClassEntity> search(ClassDTO dto, Pageable pageable);

  @Query( " select count(e) from ClassEntity e "
      + " where e.deleted = 0 and "
      + " e.subjectId = :#{#subjectId} ")
  Integer countClassWithSubject(Long subjectId);

  ClassEntity findByCode(String code);

  boolean existsByCode(String code);
}
