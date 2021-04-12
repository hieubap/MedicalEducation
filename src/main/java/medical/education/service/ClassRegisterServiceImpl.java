package medical.education.service;

import java.util.List;
import medical.education.dao.model.ClassEntity;
import medical.education.dao.model.ClassRegisterEntity;
import medical.education.dao.repository.ClassRegisterRepository;
import medical.education.dao.repository.ClassRepository;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.ClassRegisterDTO;
import medical.education.enums.ClassRegisterEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.msg.Message;
import spring.backend.library.service.AbstractBaseService;

@Service
@PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT')")
public class ClassRegisterServiceImpl extends
    AbstractBaseService<ClassRegisterEntity, ClassRegisterDTO, ClassRegisterRepository>
    implements ClassRegisterService {

  @Autowired
  private ClassRegisterRepository classRegisterRepository;

  @Autowired
  private ClassRepository classRepository;

  @Autowired
  private UserService userService;

  @Override
  protected ClassRegisterRepository getRepository() {
    return classRegisterRepository;
  }

  @Override
  protected void beforeSave(ClassRegisterEntity entity, ClassRegisterDTO dto) {
    super.beforeSave(entity, dto);
    Long studentId = userService.getCurrentUserId();
    Long classId = classRepository.findByCode(dto.getCodeClass()).getId();
    if (dto.getCodeClass() == null || !classRepository.existsByCode(dto.getCodeClass())) {
      throw new BaseException(400, "codeClass is null or not exist");
    }
    if (getRepository().existsByClassIdAndStudentId(classId, studentId)) {
      throw new BaseException(400, Message.getMessage("ClassId.Has.Registered"));
    }
    if (classRepository.findByCode(dto.getCodeClass()).getLimitRegister() <=
        classRegisterRepository.countByClassId(classId)) {
      throw new BaseException(400, Message.getMessage("Class.Is.Full"));
    }
    entity.setClassId(classId);
    entity.setStatus(ClassRegisterEnum.THANH_CONG);
  }

  @Override
  protected void afterSave(ClassRegisterEntity entity, ClassRegisterDTO dto) {
    super.afterSave(entity, dto);
    entity.setStudentId(userService.getCurrentUserId());
    entity.setClassInfo(classRepository.findByCode(dto.getCodeClass()));
    dto.setStudent(userService.getCurrentUser());
  }

  //  @Scheduled(cron = "0,20,40 * * * * *")
  public void schedule() {
    List<ClassEntity> list = (List<ClassEntity>) classRepository.findAll();
    for (ClassEntity e : list) {
      e.setCode(String.format("LOP_%04d", e.getId()));
    }
    classRepository.saveAll(list);
  }

  @Override
  public Page<ClassRegisterDTO> search(ClassRegisterDTO dto, Pageable pageable) {
    dto.setStudentId(userService.getCurrentUserId());
    return super.search(dto, pageable);
  }
}
