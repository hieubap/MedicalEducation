package medical.education.dao.repository;

import medical.education.dao.model.HealthFacilityEntity;
import medical.education.dto.HealthFacilityDTO;
import spring.backend.library.dao.repository.BaseRepository;

public interface HealthFacilityRepository extends
    BaseRepository<HealthFacilityEntity, HealthFacilityDTO, Long> {

}
