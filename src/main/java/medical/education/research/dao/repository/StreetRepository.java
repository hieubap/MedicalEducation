package medical.education.research.dao.repository;

import medical.education.research.dao.model.StreetEntity;
import medical.education.research.dto.StreetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import spring.backend.library.dao.repository.BaseRepository;

public interface StreetRepository extends BaseRepository<StreetEntity, StreetDTO,Long> {
  @Override
  @Query("select e from StreetEntity e"
      + " where (e.cityId = :#{#dto.cityId} or :#{#dto.cityId} is null)")
  Page<StreetEntity> search(StreetDTO dto, Pageable pageable);
}
