package medical.education.service;

import java.util.ArrayList;
import java.util.List;
import medical.education.dao.model.CourseEntity;
import medical.education.dao.model.ResultEntity;
import medical.education.dao.model.SubjectEntity;
import medical.education.dao.model.UserEntity;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.ResultRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.msg.Message;
import spring.backend.library.service.AbstractBaseService;

@Service
public class ResultServiceImpl extends
    AbstractBaseService<ResultEntity, ResultDTO, ResultRepository>
    implements ResultService {

  @Autowired
  private ResultRepository resultRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private CourseService courseService;

  @Autowired
  private SubjectService subjectService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private SubjectRepository subjectRepository;

  @Override
  protected ResultRepository getRepository() {
    return resultRepository;
  }

  @Override
  @PreAuthorize("hasAnyRole('TEARCHER', 'ADMIN')")
  protected void beforeSave(ResultEntity entity, ResultDTO dto) {
    super.beforeSave(entity, dto);
    if (dto.getStudentId() == null || !userRepository.existsById(dto.getStudentId())) {
      throw new BaseException(400, "studentId is null or not exist");
    }
    if (dto.getCourseId() == null || !courseRepository.existsById(dto.getCourseId())) {
      throw new BaseException(400, "courseId is null or not exist");
    }
    if (dto.getSubjectId() == null || !subjectRepository.existsById(dto.getSubjectId())) {
      throw new BaseException(400, "subjectId is null or not exist");
    }
    if (dto.getMidPoint() < 0 && dto.getMidPoint() > 10) {
      throw new BaseException(400, "0 <= Midpoint <= 10");
    }
    if (dto.getEndPoint() < 0 && dto.getEndPoint() > 10) {
      throw new BaseException(400, "0 <= Endpoint <= 10");
    }
    if (dto.getEndPoint() != null && dto.getMidPoint() != null) {
      entity.setTotal(dto.getMidPoint() * 0.3 + dto.getEndPoint() * 0.7);
    }
    if (entity.getMuster() == null) {
      dto.setMuster(0);
    }
  }

  @Override
  public void generateResult(Long courseId) {
    CourseEntity c = courseRepository.findById(courseId).get();
    List<SubjectEntity> listSubject = c.getSubjects();
    List<UserEntity> listStudent = c.getRegisters();
    List<ResultEntity> listResult = new ArrayList<>();

    for (UserEntity student : listStudent) {
      for (SubjectEntity subject : listSubject) {
        ResultEntity result = new ResultEntity();
        result.setStudent(student);
        result.setCourse(c);
        result.setSubject(subject);

        listResult.add(result);
      }
    }

    getRepository().saveAll(listResult);
  }

//  @Override
//  protected void specificMapToDTO(ResultEntity entity, ResultDTO dto) {
//    super.specificMapToDTO(entity, dto);
//    dto.setStudent(userService.findById(entity.getStudentId()));
//    dto.setCourse(courseService.findById(entity.getCourseId()));
//    dto.setSubject(subjectService.findById(entity.getSubjectId()));
//
//  }

  @Override
  public ResultDTO attendance(Long id) {
    ResultDTO result = findById(id);
    ResultEntity entity = getRepository().findById(id).get();
    if (entity.getSubject() != null &&
        result.getMuster() < entity.getSubject().getLesson()) {
      result.setMuster(result.getMuster()+1);
    }
    else{
      throw new BaseException(420, Message.getMessage("attendance.out"));
    }

    return save(result);
  }
}
