package medical.education.research.service;

import medical.education.research.dao.model.CityEntity;
import medical.education.research.dao.repository.CityRepository;
import medical.education.research.dto.CityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class CityServiceImpl extends AbstractBaseService<CityEntity, CityDTO, CityRepository>
    implements CityService {

  @Autowired
  private CityRepository repository;

  @Override
  protected CityRepository getRepository() {
    return repository;
  }

  @Override
  protected void beforeSave(CityEntity entity, CityDTO dto) {
    super.beforeSave(entity, dto);
    if (entity.getProvincialId() == null) {
      throw new BaseException(400, "provincialId is null");
    }
    if (entity.getName() == null) {
      throw new BaseException(400, "name is null");
    }

  }

}
