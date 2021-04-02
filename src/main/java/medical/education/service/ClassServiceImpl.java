package medical.education.service;

import com.google.common.base.Strings;
import java.util.List;
import java.util.Map;
import medical.education.dao.model.ClassEntity;
import medical.education.dao.repository.ClassRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.ClassDTO;
import medical.education.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
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
    if (entity.getSubjectId() == null || !subjectRepository.existsById(entity.getSubjectId())) {
      throw new BaseException(400, "subjectId is null or not exist");
    }
    /**
     * sinh mã lớp
     */
    if (Strings.isNullOrEmpty(entity.getCode())) {
      String newCode = subjectRepository.findById(entity.getSubjectId()).get().getShortName();
      Integer i = classRepository.countClassWithSubject(entity.getSubjectId());
      newCode = newCode + "_" + i;
      entity.setCode(newCode);
    }
  }

  @Override
  public ClassDTO save(Long id, Map<String, Object> map) {
    return super.save(id, map);
  }

  @Override
  public ResponseEntity approval(Long id) {
    if (!classRepository.existsById(id)) {
      throw new BaseException(400, "id is not exist");
    }

    ClassEntity classEntity = classRepository.findById(id).get();
    if (classEntity.getStatus() == (short) 3) {
      throw new BaseException(400, "class has approval");
    }

    classEntity.setStatus((short) 3);

    studyProcessService.generateLearningRoute(id);

    return new ResponseEntity(200, "successful");
  }


}
