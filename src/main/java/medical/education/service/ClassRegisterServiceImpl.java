package medical.education.service;

import medical.education.dao.model.ClassRegisterEntity;
import medical.education.dao.repository.ClassRegisterRepository;
import medical.education.dao.repository.ClassRepository;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.ClassRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class ClassRegisterServiceImpl extends
    AbstractBaseService<ClassRegisterEntity, ClassRegisterDTO, ClassRegisterRepository>
implements ClassRegisterService{
  @Autowired
  private ClassRegisterRepository classRegisterRepository;

  @Autowired
  private ClassRepository classRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  protected ClassRegisterRepository getRepository() {
    return classRegisterRepository;
  }

  @Override
  protected void beforeSave(ClassRegisterEntity entity, ClassRegisterDTO dto) {
    super.beforeSave(entity, dto);
    if (dto.getClassId() == null || !classRepository.existsById(dto.getClassId()))
      throw new BaseException(400,"classId is null or not exist");
    if (dto.getStudentId() == null || !userRepository.existsById(dto.getStudentId()))
      throw new BaseException(400,"studentId is null or not exist");

  }

  @Override
  protected void specificMapToDTO(ClassRegisterEntity entity, ClassRegisterDTO dto) {
    super.specificMapToDTO(entity, dto);
    dto.setSubjectName(entity.getClassInfo().getSubject().getName());
  }
}
