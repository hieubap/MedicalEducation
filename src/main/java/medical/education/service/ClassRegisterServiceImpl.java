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
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
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
  }

  @Override
  public void mapToEntity(ClassRegisterDTO dto, ClassRegisterEntity entity) {
    super.mapToEntity(dto, entity);
    entity.setStudentId(userService.getCurrentUserId());
  }

  @Override
  protected void afterSave(ClassRegisterEntity entity, ClassRegisterDTO dto) {
    super.afterSave(entity, dto);
    entity.setClassInfo(classRepository.findByCode(dto.getCodeClass()));
  }

  @Override
  protected void specificMapToDTO(ClassRegisterEntity entity, ClassRegisterDTO dto) {
    super.specificMapToDTO(entity, dto);
    dto.setSubjectName(entity.getClassInfo().getSubject().getName());
    dto.setSubjectCode(entity.getClassInfo().getSubject().getCode());
    dto.setStudent(userService.getCurrentUser());
  }

//  @Scheduled(cron = "0,20,40 * * * * *")
  public void schedule(){
    List<ClassEntity> list = (List<ClassEntity>) classRepository.findAll();
    for (ClassEntity e : list){
      e.setCode(String.format("LOP_%04d",e.getId()));
    }
    classRepository.saveAll(list);
  }
}
