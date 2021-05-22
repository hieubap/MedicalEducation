package medical.education.service;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import medical.education.dao.model.CourseEntity;
import medical.education.dao.model.NotificationEntity;
import medical.education.dao.model.RegisterEntity;
import medical.education.dao.model.ScheduleEntity;
import medical.education.dao.model.SubjectEntity;
import medical.education.dao.model.UserEntity;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.HealthFacilityRepository;
import medical.education.dao.repository.NotificationRepository;
import medical.education.dao.repository.RegisterRepository;
import medical.education.dao.repository.ScheduleRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.CourseDTO;
import medical.education.dto.ScheduleDTO;
import medical.education.dto.SubjectDTO;
import medical.education.dto.UserDTO;
import medical.education.enums.CourseStatusEnum;
import medical.education.enums.RegisterEnum;
import medical.education.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class CourseServiceImpl extends
    AbstractBaseService<CourseEntity, CourseDTO, CourseRepository> implements CourseService {

  @Autowired
  private CourseRepository repository;

  @Autowired
  private SubjectRepository subjectRepository;

  @Autowired
  private SubjectService subjectService;

  @Autowired
  private ScheduleService scheduleService;

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private HealthFacilityRepository healthFacilityRepository;

  @Autowired
  private NotificationRepository notificationRepository;

  @Autowired
  private RegisterRepository registerRepository;

  @Autowired
  private UserService userService;

  @Override
  protected CourseRepository getRepository() {
    return repository;
  }

  @Override
  @PreAuthorize("hasAnyRole('ADMIN')")
  protected void beforeSave(CourseEntity entity, CourseDTO dto) {
    super.beforeSave(entity, dto);

    UserDTO currentUser = userService.getCurrentUser();
    if (!currentUser.getRole().equals(RoleEnum.ADMIN)) {
      throw new BaseException(401, "Bạn không đủ quyền truy cập");
    }

    if (!Strings.isNullOrEmpty(entity.getCode()) && getRepository()
        .existsByCode(entity.getCode(), entity.getId())) {
      throw new BaseException(417, "Mã khóa học bị trùng");
    }
    if (Strings.isNullOrEmpty(entity.getCode())) {
      Long i = entity.getId() == null ? getRepository().count() : entity.getId();
      String newCode = "COURSE_" + String.format("%04d", i);
      dto.setCode(newCode);
      entity.setCode(newCode);
    }

    if (dto.getName() == null) {
      throw new BaseException(400, "name is null");
    }

    if (repository.existsByCodeAndId(dto.getCode(), dto.getId())) {
      throw new BaseException(400, "Mã đã tồn tại");
    }
    if (Strings.isNullOrEmpty(dto.getName())) {
      throw new BaseException(400, "Tên khóa học rỗng hoặc null");
    }
    if (dto.getNgayKhaiGiang() != null && dto.getNgayKetThuc() != null &&
        dto.getNgayKhaiGiang().after(dto.getNgayKetThuc())) {
      throw new BaseException(451, "Ngày khai giảng phải trước ngày kết thúc khóa");
    }
    if (dto.getSemester() == null) {
      throw new BaseException(451, "Chưa nhập kỳ học");
    }
    if (dto.getPrice() == null) {
      throw new BaseException(400, "Chưa nhập giá");
    }
    if (dto.getNumberLesson() == null) {
      throw new BaseException(400, "Chưa nhập Số tiết học");
    }
    if (dto.getLimitRegister() == null) {
      throw new BaseException(400, "Chưa nhập giới hạn đăng ký");
    }
    if (dto.getHealthFacilityId() == null || !healthFacilityRepository
        .existsById(dto.getHealthFacilityId())) {
      throw new BaseException(400, "Chưa nhập hoặc không tồn tại Cơ sở đào tạo");
    }
    if (!Strings.isNullOrEmpty(dto.getSubjectIds())) {
      List<SubjectEntity> listSubject = new ArrayList<>();
      String[] ids = dto.getSubjectIds().substring(1, dto.getSubjectIds().length() - 1)
          .split(",");
      for (String idStr : ids) {
        Long id = Long.valueOf(idStr);
        SubjectEntity e = subjectRepository.findById(id).get();
        listSubject.add(e);
      }
      entity.setSubjects(listSubject);
    }
    if (entity.getStatus() == null) {
      entity.setStatus(CourseStatusEnum.THOI_GIAN_DANG_KI.getValue());
    }
    if (entity.getNumberRegister() == null) {
      entity.setNumberRegister(0);
    }
    if (dto.getNgayKhaiGiang() != null
        && entity.getStatus().equals(CourseStatusEnum.HOAN_THANH.getValue())
        && entity.getNgayKhaiGiang().after(new Date())) {
      entity.setStatus(CourseStatusEnum.THOI_GIAN_DANG_KI.getValue());
      entity.setNumberRegister(0);
      scheduleRepository.deleteAll(entity.getSchedules());
    }
//    if (dto.getNgayKhaiGiang() != null && entity.getStatus()
//        .equals(CourseStatusEnum.DANG_HOC.getValue())) {
//      throw new BaseException(403, "Không thể thay đổi thông tin khóa học trong thời gian học");
//    }
//    if (entity.getId() != null) {
//      LocalDate ngayKhaiGiang = entity.getNgayKhaiGiang().toInstant()
//          .atZone(ZoneId.systemDefault()).toLocalDate();
//      Integer semester = ngayKhaiGiang.getYear()*100
//          + ngayKhaiGiang.getMonthValue();
//      entity.setSemester(semester);
//      registerService.changeSemester(entity.getId(), semester);
//    }
    if (getRepository().existsByCode(dto.getCode()) && dto.getId() == null) {
      throw new BaseException(400, "mã code đã tồn tại");
    }

  }

  @Override
  public void delete(Long id) {
    if (!getRepository().existsById(id)) {
      throw new BaseException(480, "Khóa học không tồn tại");
    }
    CourseEntity entity = getRepository().findById(id).get();
    if (!entity.getStatus().equals(CourseStatusEnum.THOI_GIAN_DANG_KI.getValue())) {
      throw new BaseException(481, "Không thể xóa khóa học trong thời gian học");
    } else {
      List<UserEntity> listRegister = entity.getRegisters();
      List<NotificationEntity> notifications = new ArrayList<>();
      List<RegisterEntity> registers = new ArrayList<>();

      for (UserEntity e : listRegister) {
        NotificationEntity notification = new NotificationEntity();
        notification.setContent(
            "Khóa học: " + entity.getName() + ", Kỳ học: " + entity.getSemester() + " đã bị hủy. ");
        notification.setOwnerId(e.getId());
        notifications.add(notification);

        RegisterEntity register = registerRepository
            .findByCourseIdAndStudentId(entity.getId(), e.getId());
        if (register != null) {
          register.setStatus(RegisterEnum.CANCEL);
          registers.add(register);
        }
      }

      registerRepository.saveAll(registers);
      notificationRepository.saveAll(notifications);
    }
    super.delete(id);
  }

  @Override
  protected void specificMapToDTO(CourseEntity entity, CourseDTO dto) {
    super.specificMapToDTO(entity, dto);
    if (entity.getMapAllProperties()) {
      if (entity.getSubjects() != null) {
        List<SubjectDTO> subjectDTOS = new ArrayList<>();
        for (SubjectEntity e : entity.getSubjects()) {
          subjectDTOS.add(subjectService.findById(e.getId()));
        }
        dto.setListSubject(subjectDTOS);
      }
      if (entity.getSchedules() != null) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (ScheduleEntity e : entity.getSchedules()) {
          scheduleDTOS.add(scheduleService.findById(e.getId()));
        }
        dto.setListSchedules(scheduleDTOS);
      }
    }
  }

  @Override
  @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
  public Page<CourseDTO> search(CourseDTO dto, Pageable pageable) {
    if (dto.getName() != null) {
      dto.setName("%" + dto.getName().trim().replaceAll(" ", "%") + "%");
    }
    if (dto.getCode() != null) {
      dto.setCode("%" + dto.getCode().trim().replaceAll(" ", "%") + "%");
    }
    if (dto.getNameHealthFacility() != null) {
      dto.setNameHealthFacility(
          "%" + dto.getNameHealthFacility().trim().replaceAll(" ", "%") + "%");
    }
    if (dto.getNameUserCreated() != null) {
      dto.setNameUserCreated("%" + dto.getNameUserCreated().trim().replaceAll(" ", "%") + "%");
    }
    return super.search(dto, pageable);
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void update() {
    List<CourseEntity> allCourse = repository.search(new CourseDTO(),
        PageRequest.of(0, Integer.MAX_VALUE)).toList();
    List<CourseEntity> listSave = new ArrayList<>();
    for (CourseEntity e : allCourse) {
      if (e.getNgayKhaiGiang().before(new Date())
          && e.getStatus().equals(CourseStatusEnum.THOI_GIAN_DANG_KI.getValue())) {
        e.setStatus(CourseStatusEnum.DANG_HOC.getValue());
        listSave.add(e);
      } else if (e.getNgayKetThuc().before(new Date())
          && e.getStatus().equals(CourseStatusEnum.DANG_HOC.getValue())) {
        e.setStatus(CourseStatusEnum.HOAN_THANH.getValue());
        listSave.add(e);
      }
    }

    getRepository().saveAll(listSave);
  }
}
