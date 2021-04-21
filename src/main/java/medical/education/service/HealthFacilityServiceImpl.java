package medical.education.service;

import com.google.common.base.Strings;
import medical.education.dao.model.HealthFacilityEntity;
import medical.education.dao.repository.HealthFacilityRepository;
import medical.education.dto.HealthFacilityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
@PreAuthorize("hasAnyRole('ADMIN')")
public class HealthFacilityServiceImpl extends AbstractBaseService<HealthFacilityEntity, HealthFacilityDTO, HealthFacilityRepository>
implements HealthFacilityService{
  @Autowired
  private HealthFacilityRepository healthFacilityRepository;

  @Override
  protected HealthFacilityRepository getRepository() {
    return healthFacilityRepository;
  }

  @Override
  protected void beforeSave(HealthFacilityEntity entity, HealthFacilityDTO dto) {
    super.beforeSave(entity, dto);
    if (Strings.isNullOrEmpty(dto.getName()))
      throw new BaseException(400,"name is null or empty");

    if (Strings.isNullOrEmpty(dto.getLevel()))
      throw new BaseException(400,"level is null or empty");

    if (Strings.isNullOrEmpty(dto.getAddress()))
      throw new BaseException(400,"address is null or empty");
  }
}
