package medical.education.service;

import medical.education.dao.model.CourseRegisterEntity;
import medical.education.dao.repository.CourseRegisterRepository;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.CourseRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class CourseRegisterServiceImpl extends
    AbstractBaseService<CourseRegisterEntity, CourseRegisterDTO, CourseRegisterRepository>
    implements CourseRegisterService {
  @Autowired
  private CourseRegisterRepository courseRegisterRepository;

  @Autowired
  private UserRepository studentRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Override
  protected CourseRegisterRepository getRepository() {
    return courseRegisterRepository;
  }

  @Override
  protected void beforeSave(CourseRegisterEntity entity, CourseRegisterDTO dto) {
    super.beforeSave(entity, dto);
    if (dto.getStudentId() == null || !studentRepository.existsById(dto.getStudentId()))
      throw new BaseException(400,"studentId is null or not exist");
    if (dto.getCode() == null || !courseRepository.existsByCode(dto.getCode()))
      throw new BaseException(400,"code is null or not exist");
  }
}
