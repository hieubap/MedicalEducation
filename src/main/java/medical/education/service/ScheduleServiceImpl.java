package medical.education.service;

import medical.education.dao.model.ScheduleEntity;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.PlaceRepository;
import medical.education.dao.repository.ScheduleRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.ScheduleDTO;
import medical.education.dto.UserDTO;
import medical.education.enums.KipHocEnum;
import medical.education.enums.RoleEnum;
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
public class ScheduleServiceImpl extends
    AbstractBaseService<ScheduleEntity, ScheduleDTO, ScheduleRepository>
    implements ScheduleService {

  @Autowired
  private ScheduleService scheduleService;

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private PlaceRepository placeRepository;

  @Autowired
  private PlaceService placeService;

  @Autowired
  private SubjectRepository subjectRepository;

  @Autowired
  private SubjectService subjectService;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private CourseService courseService;

  @Override
  protected ScheduleRepository getRepository() {
    return scheduleRepository;
  }

  @Override
  @PreAuthorize("hasAnyRole('ADMIN')")
  protected void beforeSave(ScheduleEntity entity, ScheduleDTO dto) {
    super.beforeSave(entity, dto);
    if (dto.getDay() == null) {
      throw new BaseException(400, Message.getMessage("data.null",new Object[]{"Thứ"}));
    }

    if (dto.getPlaceId() == null || !placeRepository.existsById(dto.getPlaceId())) {
      throw new BaseException(400, Message.getMessage("data.null",new Object[]{"Địa điểm"}));
    }

    if (dto.getSubjectId() == null || !subjectRepository.existsById(dto.getSubjectId())) {
      throw new BaseException(400, Message.getMessage("data.null",new Object[]{"Môn học"}));
    }

    if (dto.getCourseId() == null || !courseRepository.existsById(dto.getCourseId())) {
      throw new BaseException(400, Message.getMessage("data.null",new Object[]{"Khóa học"}));
    }

    if (dto.getTeacherId() == null || !userRepository.existsById(dto.getTeacherId())) {
      throw new BaseException(400, Message.getMessage("data.null",new Object[]{"Giảng viên"}));
    }
    if (dto.getKipHoc() == null) {
      throw new BaseException(400, Message.getMessage("data.null",new Object[]{"Kíp học"}));
    }
  }

  private void kiemTraLopHoc(Short kipHoc, Short day, Long courseId) {
    if (getRepository().checkExistByDayAndKipHoc(kipHoc, day, courseId)) {
      throw new BaseException(400, "Trùng lịch");
    }
  }

  @Override
  protected void afterSave(ScheduleEntity entity, ScheduleDTO dto) {
    super.afterSave(entity, dto);
    entity.setSubject(subjectRepository.findById(entity.getSubjectId()).get());
    entity.setPlace(placeRepository.findById(entity.getPlaceId()).get());
    entity.setTeacher(userRepository.findById(entity.getTeacherId()).get());
  }

  @Override
  protected void specificMapToDTO(ScheduleEntity entity, ScheduleDTO dto) {
    super.specificMapToDTO(entity, dto);

    dto.setCourseInfo(courseService.findById(entity.getCourseId()));
    dto.setSubjectInfo(subjectService.findById(entity.getSubjectId()));
    dto.setPlaceInfo(placeService.findById(entity.getPlaceId()));
    dto.setTeacher(userService.findById(entity.getTeacherId()));
  }

  @Override
  // sinh viên xem lịch
  public Page<ScheduleDTO> getSchedule() {
    ScheduleDTO scheduleSearch = new ScheduleDTO();
    scheduleSearch.setCourseId(userService.getCurrentUser().getCurrentCourseId());
    return search(scheduleSearch, PageRequest.of(0, Integer.MAX_VALUE));
  }

  @Override
  public Page<ScheduleDTO> getListClass(ScheduleDTO dto, Pageable page) {
    UserDTO currentUser = userService.getCurrentUser();
    if (!currentUser.getRole().equals(RoleEnum.TEACHER)) {
      throw new BaseException(400, Message.getMessage("not.teacher"));
    }

    dto.setTeacherId(currentUser.getId());
    return super.search(dto, page);
  }

  @Override
  public Page<ScheduleDTO> getSchedulebBusy() {
    ScheduleDTO scheduleSearch = new ScheduleDTO();
    scheduleSearch.setStatus((short) 2);
    return search(scheduleSearch, PageRequest.of(0, Integer.MAX_VALUE));
  }
}
