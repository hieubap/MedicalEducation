package medical.education.service;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import medical.education.dao.model.ClassEntity;
import medical.education.dao.model.ClassRegisterEntity;
import medical.education.dao.repository.ClassRegisterRepository;
import medical.education.dao.repository.ClassRepository;
import medical.education.dao.repository.PlaceRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dao.repository.UserRepository;
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

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PlaceRepository placeRepository;

  @Autowired
  private ClassRegisterRepository classRegisterRepository;

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
    if (entity.getPlaceId() == null || !placeRepository.existsById(entity.getPlaceId())) {
      throw new BaseException(400, "placeId is null or not exist");
    }
    if (entity.getTeacherId() == null || !userRepository.existsById(entity.getTeacherId())) {
      throw new BaseException(400, "teacherId is null or not exist");
    }
    if (Strings.isNullOrEmpty(entity.getTime())) {
      throw new BaseException(400, "time is null or empty");
    }
    if (entity.getLimitRegister() == null) {
      throw new BaseException(400, "numberRegister is null or empty");
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
    if (entity.getStatus() == null){
      entity.setStatus((short)5);
    }
  }

  @Override
  public ClassDTO save(Long id, Map<String, Object> map) {
    return super.save(id, map);
  }

  @Override
  protected void afterSave(ClassEntity entity, ClassDTO dto) {
    super.afterSave(entity, dto);
    entity.setSubject(subjectRepository.findById(dto.getSubjectId()).get());
    entity.setTeacher(userRepository.findById(dto.getTeacherId()).get());
    entity.setPlace(placeRepository.findById(dto.getPlaceId()).get());
  }

  @Override
  protected void specificMapToDTO(ClassEntity entity, ClassDTO dto) {
    super.specificMapToDTO(entity, dto);
    dto.setNumberRegister(classRegisterRepository.countByClassId(entity.getId()));
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

  @Override
  public ResponseEntity cancel(Long id) {
    ClassEntity entity = classRepository.findById(id).get();
    entity.setStatus((short)2);

    List<ClassRegisterEntity> list = classRegisterRepository.findByClassId(id);
    for (ClassRegisterEntity e : list){
      e.setStatus((short)1 );
    }
    classRegisterRepository.saveAll(list);

    return new ResponseEntity(200,"cancel class '"+entity.getCode()+"' successful");
  }
}
