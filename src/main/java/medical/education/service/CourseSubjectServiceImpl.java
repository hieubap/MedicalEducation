package medical.education.service;

import medical.education.dao.model.CourseSubjectEntity;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.CourseSubjectRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.CourseSubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class CourseSubjectServiceImpl extends
    AbstractBaseService<CourseSubjectEntity, CourseSubjectDTO, CourseSubjectRepository> implements
    CourseSubjectService {
  @Autowired
  private CourseSubjectRepository courseSubjectRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private SubjectRepository subjectRepository;

  @Override
  protected CourseSubjectRepository getRepository() {
    return courseSubjectRepository;
  }

  @Override
  protected void beforeSave(CourseSubjectEntity entity, CourseSubjectDTO dto) {
    super.beforeSave(entity, dto);
    if (dto.getSubjectId() == null || !subjectRepository.existsById(dto.getSubjectId()))
      throw new BaseException(400,"subjectId is null or not exist");
    if (dto.getCourseId() == null || !courseRepository.existsById(dto.getCourseId()))
      throw new BaseException(400,"courseId is null or not exist");
    if (courseSubjectRepository.exist(dto.getCourseId(),dto.getSubjectId()))
      throw new BaseException(400,"subject is exist on course");
  }

  @Override
  protected void afterSave(CourseSubjectEntity entity, CourseSubjectDTO dto) {
    super.afterSave(entity, dto);
    entity.setSubject(subjectRepository.findById(entity.getSubjectId()).get());
  }

  @Override
  public void delete(Long courseId, Long subjectId) {
    getRepository().deleteByCourseIdAndSubjectId(courseId,subjectId);
  }
}
