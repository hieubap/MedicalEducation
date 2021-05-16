package medical.education.service;

import java.util.List;
import medical.education.dao.model.RegisterEntity;
import medical.education.dao.model.ResultEntity;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.RegisterRepository;
import medical.education.dao.repository.ResultRepository;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.RegisterDTO;
import medical.education.dto.ResultDTO;
import medical.education.dto.UserDTO;
import medical.education.enums.RegisterEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

  @Autowired
  private ResultService resultService;

  @Autowired
  private ResultRepository resultRepository;

  @Override
  protected RegisterRepository getRepository() {
    return registerRepository;
  }

  @Override
  protected void beforeSave(RegisterEntity entity, RegisterDTO dto) {
    super.beforeSave(entity, dto);
    entity.setStudentId(userService.getCurrentUserId());
//    if (dto.getCode() == null || !courseRepository.existsByCode(dto.getCode())) {
//      throw new BaseException(400, Message.getMessage("Null.Or.Not.Exist", new Object[]{"code"}));
//    }
    if (dto.getCourseId() == null || !courseRepository.existsById(dto.getCourseId())) {
      throw new BaseException(400, Message.getMessage("Null.Or.Not.Exist", new Object[]{"code"}));
    }
//    CourseEntity courseEntity = courseRepository.findByCode(dto.getCode());

//    if (e != null && !e.getStatus().equals(RegisterEnum.DONED)) {
//      throw new BaseException(400,
//          Message.getMessage("Has.Register.Course", new Object[]{e.getCourse().getName()}));
//    }
    UserDTO currentUser = userService.getCurrentUser();
    RegisterEntity e = registerRepository.findByCourseIdAndStudentId(
        currentUser.getCurrentCourseId(), userService.getCurrentUserId());
    if (currentUser.getCurrentCourseId() != null) {
      throw new BaseException(410,
          Message.getMessage("Has.Register.Course", new Object[]{e.getCourse().getName()}));
    } else {
      currentUser.setCurrentCourseId(dto.getCourseId());
      userService.save(currentUser.getId(), currentUser);
    }
    entity.setStatus(RegisterEnum.REGISTER_DONED);
  }

  @Override
  protected void afterSave(RegisterEntity entity, RegisterDTO dto) {
    super.afterSave(entity, dto);
    resultService.generateResultsForStudent(entity.getCourseId(), entity.getStudentId());
    entity.setCourse(courseRepository.findById(entity.getCourseId()).get());
    entity.setStudent(userRepository.findById(entity.getStudentId()).get());
  }

  @Override
  public Page<RegisterDTO> search(RegisterDTO dto, Pageable pageable) {
    dto.setStudentId(userService.getCurrentUserId());
    return super.search(dto, pageable);
  }

  @Override
  public void delete(Long id) {
    RegisterEntity entity = getRepository().findById(id).get();
    if (!entity.getStatus().equals(RegisterEnum.REGISTER_DONED)) {
      throw new BaseException(411, Message.getMessage("Cant.cancel.course"));
    }
    UserDTO currentUser = userService.getCurrentUser();

    ResultDTO searchDTO = new ResultDTO();
    searchDTO.setStudentId(currentUser.getId());
    searchDTO.setCourseId(entity.getCourseId());
    List<ResultEntity> listResult = resultRepository
        .search(searchDTO, PageRequest.of(0, 999999)).toList();

    resultRepository.deleteAll(listResult);

    currentUser.setCurrentCourseId(null);
    userService.save(currentUser);


    super.delete(id);
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
