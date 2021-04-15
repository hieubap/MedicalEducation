package medical.education.research.service;

import medical.education.research.dao.model.ProvincialEntity;
import medical.education.research.dao.repository.ProvincialRepository;
import medical.education.research.dto.ProvincialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class ProvincialServiceImpl extends
    AbstractBaseService<ProvincialEntity, ProvincialDTO, ProvincialRepository>
    implements ProvincialService {

  @Autowired
  private ProvincialRepository repository;

  @Override
  protected ProvincialRepository getRepository() {
    return repository;
  }

  @Override
  protected void beforeSave(ProvincialEntity entity, ProvincialDTO dto) {
    super.beforeSave(entity, dto);
    if (entity.getName() == null) {
      throw new BaseException(400, "name is null");
    }
  }
}
