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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
@PreAuthorize("hasAnyRole('TEARCHER', 'ADMIN', 'USER')")
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
      throw new BaseException(400, "code is null or not exist");
    }
    entity.setCourseId(courseRepository.findByCode(dto.getCode()).getId());
  }

  @Override
  protected void afterSave(CourseRegisterEntity entity, CourseRegisterDTO dto) {
    super.afterSave(entity, dto);
    entity.setCourse(courseRepository.findById(entity.getCourseId()).get());
    entity.setStudent(userRepository.findById(entity.getStudentId()).get());

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
