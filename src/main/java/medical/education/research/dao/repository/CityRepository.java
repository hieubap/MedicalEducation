package medical.education.research.dao.repository;

import medical.education.research.dao.model.CityEntity;
import medical.education.research.dto.CityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import spring.backend.library.dao.repository.BaseRepository;

public interface CityRepository extends BaseRepository<CityEntity, CityDTO,Long> {

  @Override
  @Query("select e from CityEntity e"
      + " where (e.provincialId = :#{#dto.provincialId} or :#{#dto.provincialId} is null)")
  Page<CityEntity> search(CityDTO dto, Pageable pageable);
}
