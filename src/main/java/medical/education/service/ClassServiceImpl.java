package medical.education.service;

import medical.education.dao.model.ClassEntity;
import medical.education.dao.repository.ClassRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.ClassDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class ClassServiceImpl extends AbstractBaseService<ClassEntity, ClassDTO, ClassRepository>
implements ClassService {
  @Autowired
  private ClassRepository classRepository;

  @Autowired
  private SubjectRepository subjectRepository;

  @Override
  protected ClassRepository getRepository() {
    return classRepository;
  }

  @Override
  protected void beforeSave(ClassEntity entity, ClassDTO dto) {
    super.beforeSave(entity, dto);
    if (dto.getSubjectId() == null || !subjectRepository.existsById(dto.getSubjectId()))
      throw new BaseException(400,"subjectId is null or not exist");

  }
}
