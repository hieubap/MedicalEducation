package medical.education.service;

import java.util.ArrayList;
import java.util.List;
import medical.education.dao.model.ClassEntity;
import medical.education.dao.model.CourseEntity;
import medical.education.dao.model.ResultEntity;
import medical.education.dao.model.SubjectEntity;
import medical.education.dao.model.UserEntity;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.CourseSubjectRepository;
import medical.education.dao.repository.ResultRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
@PreAuthorize("hasAnyRole('TEARCHER', 'ADMIN')")
public class ResultServiceImpl extends
    AbstractBaseService<ResultEntity, ResultDTO, ResultRepository>
    implements ResultService {
  @Autowired
  private ResultRepository resultRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private SubjectRepository subjectRepository;

  @Autowired
  private CourseSubjectRepository courseSubjectRepository;

  @Autowired
  private SubjectServiceImpl subjectService;

  @Override
  protected ResultRepository getRepository() {
    return resultRepository;
  }

  @Override
  protected void beforeSave(ResultEntity entity, ResultDTO dto) {
    super.beforeSave(entity, dto);
    if (dto.getStudentId() == null || !userRepository.existsById(dto.getStudentId()))
      throw new BaseException(400,"studentId is null or not exist");
    if (dto.getCourseId() == null || !courseRepository.existsById(dto.getCourseId()))
      throw new BaseException(400,"courseId is null or not exist");
    if (dto.getSubjectId() == null || !subjectRepository.existsById(dto.getSubjectId()))
      throw new BaseException(400,"subjectId is null or not exist");

  }

  @Override
  public void generateResult(Long courseId) {
    CourseEntity c = courseRepository.findById(courseId).get();
    List<SubjectEntity> listSubject = c.getSubjects();
    List<UserEntity> listStudent = c.getRegisters();
    List<ResultEntity> listResult = new ArrayList<>();

    for(UserEntity student : listStudent){
      for(SubjectEntity subject : listSubject){
        ResultEntity result = new ResultEntity();
        result.setStudent(student);
        result.setCourse(c);
        result.setSubject(subject);

        listResult.add(result);
      }
    }

    getRepository().saveAll(listResult);
  }

  @Override
  protected void specificMapToDTO(ResultEntity entity, ResultDTO dto) {
    super.specificMapToDTO(entity, dto);
    dto.setSubject(subjectService.mapToDTO(entity.getSubject()));
  }
}
