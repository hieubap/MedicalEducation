package medical.education.research.service;

import medical.education.research.dao.model.StreetEntity;
import medical.education.research.dao.repository.StreetRepository;
import medical.education.research.dto.StreetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class StreetServiceImpl extends AbstractBaseService<StreetEntity, StreetDTO, StreetRepository>
implements StreetService{
  @Autowired
  private StreetRepository streetRepository;
  @Override
  protected StreetRepository getRepository() {
    return streetRepository;
  }

  @Override
  protected void beforeSave(StreetEntity entity, StreetDTO dto) {
    super.beforeSave(entity, dto);
    if (entity.getCityId() == null)
      throw new BaseException(400,"cityId is null");
    if (entity.getName() == null)
      throw new BaseException(400,"name is null");

  }
}