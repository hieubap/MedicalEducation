package medical.education.service;

import com.google.common.base.Strings;
import medical.education.dao.model.SubjectEntity;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class SubjectServiceImpl extends
    AbstractBaseService<SubjectEntity, SubjectDTO, SubjectRepository> implements SubjectService {

  @Autowired
  private SubjectRepository repository;

  @Override
  protected SubjectRepository getRepository() {
    return repository;
  }

  @Override
  public SubjectEntity findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  protected void beforeSave(SubjectEntity entity, SubjectDTO dto) {
    super.beforeSave(entity, dto);
    if (Strings.isNullOrEmpty(dto.getName())){
      throw new BaseException("name is null");
    }
    if (Strings.isNullOrEmpty(dto.getType())){
      throw new BaseException("type is null");
    }
  }
  public SubjectEntity findEntityByid(Long id) {
    return repository.findById(id).orElse(null);
  }
}
