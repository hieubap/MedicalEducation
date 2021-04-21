package medical.education.service;

import medical.education.dao.model.CourseEntity;
import medical.education.dao.model.RegisterEntity;
import medical.education.dao.repository.RegisterRepository;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.RegisterDTO;
import medical.education.enums.RegisterEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.msg.Message;
import spring.backend.library.service.AbstractBaseService;

@Service
@PreAuthorize("hasAnyRole('TEARCHER', 'ADMIN', 'STUDENT')")
public class RegisterServiceImpl extends
    AbstractBaseService<RegisterEntity, RegisterDTO, RegisterRepository>
    implements RegisterService {

  @Autowired
  private RegisterRepository registerRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  protected RegisterRepository getRepository() {
    return registerRepository;
  }

  @Override
  protected void beforeSave(RegisterEntity entity, RegisterDTO dto) {
    super.beforeSave(entity, dto);
    entity.setStudentId(userService.getCurrentUserId());
    if (dto.getCode() == null || !courseRepository.existsByCode(dto.getCode())) {
      throw new BaseException(400, Message.getMessage("Null.Or.Not.Exist", new Object[]{"code"}));
    }
    CourseEntity courseEntity = courseRepository.findByCode(dto.getCode());
    RegisterEntity e = registerRepository.findByCourseIdAndStudentId(
        courseEntity.getId(), userService.getCurrentUserId());
    if (e != null && !e.getStatus().equals(RegisterEnum.DONED)) {
      throw new BaseException(400,
          Message.getMessage("Has.Register.Course", new Object[]{e.getCourse().getName()}));
    }
    entity.setCourseId(courseRepository.findByCode(dto.getCode()).getId());
    entity.setStatus(RegisterEnum.STUDYING);
  }

  @Override
  protected void afterSave(RegisterEntity entity, RegisterDTO dto) {
    super.afterSave(entity, dto);
    entity.setCourse(courseRepository.findById(entity.getCourseId()).get());
    entity.setStudent(userRepository.findById(entity.getStudentId()).get());
  }

  @Override
  public Page<RegisterDTO> search(RegisterDTO dto, Pageable pageable) {
    dto.setStudentId(userService.getCurrentUserId());
    return super.search(dto, pageable);
  }

  //  @Scheduled(cron = "0,20,40 * * * * *")
//  public void schedule() {
//    List<CourseEntity> list = (List<CourseEntity>) courseRepository.findAll();
//    for (CourseEntity c : list) {
//      String s = Normalizer.normalize(c.getName(), Normalizer.Form.NFD);
//      s = s.toUpperCase().replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
//
//      String shortName = s.charAt(0) + "" + s.charAt(1);
//
//      c.setCode(shortName+ String.format("_%04d", c.getId()));
//    }
//    courseRepository.saveAll(list);
//  }
}
