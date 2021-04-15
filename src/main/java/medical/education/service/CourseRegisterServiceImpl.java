package medical.education.service;

import java.text.Normalizer;
import java.util.List;
import medical.education.dao.model.CourseEntity;
import medical.education.dao.model.CourseRegisterEntity;
import medical.education.dao.model.SubjectEntity;
import medical.education.dao.repository.CourseRegisterRepository;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.CourseRegisterDTO;
import medical.education.enums.CourseRegisterEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.msg.Message;
import spring.backend.library.service.AbstractBaseService;

@Service
@PreAuthorize("hasAnyRole('TEARCHER', 'ADMIN', 'STUDENT')")
public class CourseRegisterServiceImpl extends
    AbstractBaseService<CourseRegisterEntity, CourseRegisterDTO, CourseRegisterRepository>
    implements CourseRegisterService {

  @Autowired
  private CourseRegisterRepository courseRegisterRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  protected CourseRegisterRepository getRepository() {
    return courseRegisterRepository;
  }

  @Override
  protected void beforeSave(CourseRegisterEntity entity, CourseRegisterDTO dto) {
    super.beforeSave(entity, dto);
    entity.setStudentId(userService.getCurrentUserId());
    if (dto.getCode() == null || !courseRepository.existsByCode(dto.getCode())) {
      throw new BaseException(400, Message.getMessage("Null.Or.Not.Exist", new Object[]{"code"}));
    }
    CourseEntity courseEntity = courseRepository.findByCode(dto.getCode());
    CourseRegisterEntity e = courseRegisterRepository.findByCourseIdAndStudentId(
        courseEntity.getId(), userService.getCurrentUserId());
    if (e != null && !e.getStatus().equals(CourseRegisterEnum.DONE.getValue())) {
      throw new BaseException(400,
          Message.getMessage("Has.Register.Course", new Object[]{e.getCourse().getName()}));
    }
    entity.setCourseId(courseRepository.findByCode(dto.getCode()).getId());
    entity.setStatus(CourseRegisterEnum.WAIT_APPROVE);
  }

  @Override
  protected void afterSave(CourseRegisterEntity entity, CourseRegisterDTO dto) {
    super.afterSave(entity, dto);
    entity.setCourse(courseRepository.findById(entity.getCourseId()).get());
    entity.setStudent(userRepository.findById(entity.getStudentId()).get());

  }

  @Override
  public Page<CourseRegisterDTO> search(CourseRegisterDTO dto, Pageable pageable) {
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
