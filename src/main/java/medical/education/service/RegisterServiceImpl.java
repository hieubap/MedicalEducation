package medical.education.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import medical.education.dao.model.CourseEntity;
import medical.education.dao.model.RegisterEntity;
import medical.education.dao.model.ResultEntity;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.RegisterRepository;
import medical.education.dao.repository.ResultRepository;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.RegisterDTO;
import medical.education.dto.ResultDTO;
import medical.education.dto.UserDTO;
import medical.education.enums.CourseStatusEnum;
import medical.education.enums.RegisterEnum;
import medical.education.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.msg.Message;
import spring.backend.library.service.AbstractBaseService;

@Service
//@PreAuthorize("hasAnyRole('TEARCHER', 'ADMIN', 'STUDENT')")
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
  private CourseService courseService;

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
    UserDTO currentUser = userService.getCurrentUser();
    if (dto.getCourseId() == null || !courseRepository.existsById(dto.getCourseId())) {
      throw new BaseException(400, Message.getMessage("Null.Or.Not.Exist", new Object[]{"code"}));
    }

    if (currentUser.getRole().equals(RoleEnum.ADMIN)) {
      if (dto.getSemester() == null) {
        throw new BaseException(410, "semester is null");
      }
      if (dto.getCourseId() == null) {
        throw new BaseException(410, "courseId is null");
      }
      if (dto.getStudentId() == null) {
        throw new BaseException(410, "studentId is null");
      }
      if (dto.getStatus() == null) {
        throw new BaseException(410, "status is null");
      }
    } else {
      entity.setStudentId(userService.getCurrentUserId());
//    CourseEntity courseEntity = courseRepository.findByCode(dto.getCode());

//    if (e != null && !e.getStatus().equals(RegisterEnum.DONED)) {
//      throw new BaseException(400,
//          Message.getMessage("Has.Register.Course", new Object[]{e.getCourse().getName()}));
//    }

      RegisterEntity e = registerRepository.findByCourseIdAndStudentId(
          currentUser.getCurrentCourseId(), userService.getCurrentUserId());
      if (currentUser.getCurrentCourseId() != null) {
        throw new BaseException(410,
            Message.getMessage("Has.Register.Course", new Object[]{e.getCourse().getName()}));
      } else {
        currentUser.setCurrentCourseId(dto.getCourseId());
        CourseEntity entityCourse = courseRepository.findById(dto.getCourseId()).get();
        if (entityCourse.getNumberRegister() >= entityCourse.getLimitRegister()) {
          throw new BaseException(430, "Khóa học đã quá giới hạn đăng ký");
        }
        if (entity.getStatus() == null && !entityCourse.getStatus()
            .equals(CourseStatusEnum.THOI_GIAN_DANG_KI.getValue())) {
          throw new BaseException(431, "Chỉ có thể đăng ký khóa trong thời gian đăng ký");
        }
        entityCourse.setNumberRegister(entityCourse.getNumberRegister() + 1);

//      LocalDate ngayKhaiGiang = entityCourse.getNgayKhaiGiang().toInstant()
//          .atZone(ZoneId.systemDefault()).toLocalDate();
//      Integer semester = ngayKhaiGiang.getYear() * 100
//          + ngayKhaiGiang.getMonthValue();
        entity.setSemester(entityCourse.getSemester());

        courseRepository.save(entityCourse);
        userService.save(currentUser.getId(), currentUser);
      }
      entity.setStatus(RegisterEnum.REGISTER_DONED);
    }
  }

  @Override
  public void changeSemester(Long courseId, Integer semester) {
    List<RegisterEntity> listChange = registerRepository.findRegistersToChangeSemester(courseId);
    for (RegisterEntity e : listChange) {
      e.setSemester(semester);
    }
    registerRepository.saveAll(listChange);
  }

  @Override
  protected void afterSave(RegisterEntity entity, RegisterDTO dto) {
    super.afterSave(entity, dto);
    resultService
        .generateResultsForStudent(entity.getCourseId(), entity.getStudentId(), entity.getId());
    entity.setCourse(courseRepository.findById(entity.getCourseId()).get());
    entity.setStudent(userRepository.findById(entity.getStudentId()).get());
  }

  @Override
  public Page<RegisterDTO> search(RegisterDTO dto, Pageable pageable) {
//    dto.setStudentId(userService.getCurrentUserId());
    return super.search(dto, pageable);
  }

  @Override
  protected void specificMapToDTO(RegisterEntity entity, RegisterDTO dto) {
    super.specificMapToDTO(entity, dto);
    dto.setStudentInfo(userService.findById(entity.getStudentId()));
    dto.setCourseInfo(courseService.findById(entity.getCourseId()));
  }

  @Override
  public List<Integer> getListSemester(Long courseId) {
    return getRepository().getListSemester(courseId);
  }

  ;

  @Override
  public void delete(Long id) {
    RegisterEntity entity = getRepository().findById(id).get();
    if (!entity.getStatus().equals(RegisterEnum.REGISTER_DONED)) {
      throw new BaseException(411, Message.getMessage("Cant.cancel.course"));
    }
    UserDTO currentUser = userService.getCurrentUser();

    CourseEntity courseEntity = courseRepository.findById(entity.getCourseId()).get();
    courseEntity.setNumberRegister(courseEntity.getNumberRegister() - 1);
    courseRepository.save(courseEntity);

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

  @Scheduled(cron = "0 0 1 * * *")
  public void update() {
    List<RegisterEntity> allRegister = StreamSupport
        .stream(getRepository().findAll().spliterator(), false)
        .collect(Collectors.toList());
    List<RegisterEntity> listSave = new ArrayList<>();
    for (RegisterEntity e : allRegister) {
      if (e.getCourse().getStatus().equals(CourseStatusEnum.DANG_HOC.getValue()) &&
          e.getStatus().equals(RegisterEnum.REGISTER_DONED)) {
        e.setStatus(RegisterEnum.STUDYING);
        listSave.add(e);
      } else if (e.getCourse().getStatus().equals(CourseStatusEnum.HOAN_THANH.getValue()) &&
          e.getStatus().equals(RegisterEnum.STUDYING)) {
        e.setStatus(RegisterEnum.WAIT_TEACHER);
        listSave.add(e);
      }

      // tính điểm trung bình
      Double total = 0.0;
      int count = 0;
      for (ResultEntity r : e.getResults()) {
        if (r.getTotal() != null) {
          total += r.getTotal();
          count++;
        }
      }
      if (count > 0) {
        total = total / count;
        e.setTotal(total);
        e.setKind(applyKind(total));
      }
    }
    registerRepository.saveAll(listSave);
  }

  public String applyKind(Double total) {
    if (total > 8) {
      return "Giỏi";
    }
    if (total > 6.25) {
      return "Khá";
    }
    if (total > 5) {
      return "Trung Bình";
    }
    return "Kém";
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
