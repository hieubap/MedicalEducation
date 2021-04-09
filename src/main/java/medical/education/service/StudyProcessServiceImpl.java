package medical.education.service;
import medical.education.dao.model.RoleEntity;
import com.google.common.collect.Lists;
import medical.education.enums.Gender;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import medical.education.dao.model.ClassEntity;
import medical.education.dao.model.StudyProcessEntity;
import medical.education.dao.model.UserEntity;
import medical.education.dao.repository.ClassRepository;
import medical.education.dao.repository.StudyProcessRepository;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.StudyProcessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
@PreAuthorize("hasAnyRole('TEARCHER', 'ADMIN')")
public class StudyProcessServiceImpl extends
    AbstractBaseService<StudyProcessEntity, StudyProcessDTO, StudyProcessRepository>
    implements StudyProcessService {
  @Autowired
  private StudyProcessRepository studyProcessRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ClassRepository classRepository;

  @Autowired
  private SubjectServiceImpl subjectService;

  @Override
  protected StudyProcessRepository getRepository() {
    return studyProcessRepository;
  }

  @Override
  protected void beforeSave(StudyProcessEntity entity, StudyProcessDTO dto) {
    super.beforeSave(entity, dto);
    if (dto.getStudentId() == null || !userRepository.existsById(dto.getStudentId()))
      throw new BaseException(400,"studentId is null or not exist");
    if (dto.getClassId() == null || !classRepository.existsById(dto.getClassId()))
      throw new BaseException(400,"classId is null or not exist");

  }

  @Override
  public void generateLearningRoute(Long classId) {
    ClassEntity c = classRepository.findById(classId).get();
    List<StudyProcessEntity> listStudent = new ArrayList<>();
    for(UserEntity user : c.getRegister()){
      StudyProcessEntity u = new StudyProcessEntity();
      u.setStudent(user);
      u.setClazz(c);
      listStudent.add(u);
    }

    getRepository().saveAll(listStudent);
  }

  @Override
  protected void specificMapToDTO(StudyProcessEntity entity, StudyProcessDTO dto) {
    super.specificMapToDTO(entity, dto);
    dto.setSubject(subjectService.mapToDTO(entity.getClazz().getSubject()));
  }
}
