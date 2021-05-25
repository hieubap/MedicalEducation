package medical.education.dao.repository;


import medical.education.dao.model.ProgramEntity;
import medical.education.dto.ProgramDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import spring.backend.library.dao.repository.BaseRepository;

public interface ProgramRepository extends BaseRepository<ProgramEntity, ProgramDTO, Long> {
  @Override
  @Query(" select e from ProgramEntity e "
      + " where (e.id = :#{#dto.id} or :#{#dto.id} is null)"
      + " and (lower(e.name) like :#{#dto.name} or :#{#dto.name} is null)"
      + " and (:#{#dto.numberTurn} = (select count(c) from CourseEntity c where (c.id = e.id))"
      + "   or :#{#dto.name} is null)")
  Page<ProgramEntity> search(ProgramDTO dto, Pageable pageable);

  boolean existsByCodeAndId(String code,Long id);
}
