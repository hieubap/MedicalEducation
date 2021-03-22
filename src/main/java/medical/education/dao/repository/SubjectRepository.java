package medical.education.dao.repository;

import medical.education.dao.model.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import spring.backend.library.dao.repository.BaseRepository;
import medical.education.dto.SubjectDTO;

public interface SubjectRepository extends BaseRepository<SubjectEntity, SubjectDTO, Long> {
  @Override
  @Query("select e from SubjectEntity e "
      + " where (e.name like :#{#dto.name} or :#{#dto.name} is null) "
      + " and (e.type = :#{#dto.type} or :#{#dto.type} is null)"
      + " and (e.value = :#{#dto.value} or :#{#dto.value} is null)")
  Page<SubjectEntity> search(SubjectDTO dto, Pageable pageable);
}
