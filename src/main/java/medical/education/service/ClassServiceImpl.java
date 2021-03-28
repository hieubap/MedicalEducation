package medical.education.service;

import java.util.Map;
import medical.education.dao.model.ClassEntity;
import medical.education.dao.repository.ClassRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.ClassDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.dto.ResponseEntity;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class ClassServiceImpl extends AbstractBaseService<ClassEntity, ClassDTO, ClassRepository>
implements ClassService {
  @Autowired
  private ClassRepository classRepository;

  @Autowired
  private SubjectRepository subjectRepository;

  @Autowired
  private StudyProcessService studyProcessService;

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

  @Override
  public ClassDTO save(Long id, Map<String, Object> map) {
    return super.save(id, map);
  }

  @Override
  public ResponseEntity approval(Long id) {
    if (!classRepository.existsById(id))
      throw new BaseException(400,"id is not exist");

    ClassEntity classEntity = classRepository.findById(id).get();
    if (classEntity.getStatus() == (short) 3 )
      throw new BaseException(400,"class has approval");

    classEntity.setStatus((short) 3);

    studyProcessService.generateLearningRoute(id);

    return new ResponseEntity(200,"successful");
  }
}
