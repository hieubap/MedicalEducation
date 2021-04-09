package medical.education.service;

import java.util.List;
import medical.education.dao.model.ClassEntity;
import medical.education.dao.model.ClassRegisterEntity;
import medical.education.dao.repository.ClassRegisterRepository;
import medical.education.dao.repository.ClassRepository;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.ClassRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
@PreAuthorize("hasAnyRole('TEARCHER', 'ADMIN', 'USER')")
public class ClassRegisterServiceImpl extends
    AbstractBaseService<ClassRegisterEntity, ClassRegisterDTO, ClassRegisterRepository>
    implements ClassRegisterService {

  @Autowired
  private ClassRegisterRepository classRegisterRepository;

  @Autowired
  private ClassRepository classRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Override
  protected ClassRegisterRepository getRepository() {
    return classRegisterRepository;
  }

  @Override
  protected void beforeSave(ClassRegisterEntity entity, ClassRegisterDTO dto) {
    super.beforeSave(entity, dto);
    if (dto.getCodeClass() == null || !classRepository.existsByCode(dto.getCodeClass())) {
      throw new BaseException(400, "codeClass is null or not exist");
    }
    entity.setClassId(classRepository.findByCode(dto.getCodeClass()).getId());
    entity.setStatus((short) 0);
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
}
