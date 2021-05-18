package medical.education.service;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import medical.education.dao.model.CourseEntity;
import medical.education.dao.model.ScheduleEntity;
import medical.education.dao.model.SubjectEntity;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.HealthFacilityRepository;
import medical.education.dao.repository.ScheduleRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.CourseDTO;
import medical.education.dto.ScheduleDTO;
import medical.education.dto.SubjectDTO;
import medical.education.enums.CourseStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring.backend.library.config.userdetail.Authority;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
//@PreAuthorize("hasRole('ADMIN')")
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

  @Override
  protected CourseRepository getRepository() {
    return repository;
  }

  @Override
  @PreAuthorize("hasAnyRole('ADMIN')")
  protected void beforeSave(CourseEntity entity, CourseDTO dto) {
    super.beforeSave(entity, dto);

    if (Strings.isNullOrEmpty(entity.getCode())) {
      Long i = getRepository().count();
      String newCode = "COURSE_" + String.format("%04d", i);
      entity.setCode(newCode);
    }
    if (dto.getName() == null) {
      throw new BaseException(400, "name is null");
    }
//    if (dto.getNgayKhaiGiang()) {
//      throw new BaseException(450, "ngayKhaiGiang is null");
//    }

    if (dto.getNgayKhaiGiang() != null && dto.getNgayKetThuc() != null &&
        dto.getNgayKhaiGiang().after(dto.getNgayKetThuc())
    ) {
      throw new BaseException(451, "ngayKhaiGiang after ngayKetThuc");
    }

    if (dto.getPrice() == null) {
      throw new BaseException(400, "price is null");
    }
    if (dto.getNumberLesson() == null) {
      throw new BaseException(400, "numberLesson is null");
    }

    if (dto.getLimitRegister() == null) {
      throw new BaseException(400, "limitRegister is null");
    }

    if (dto.getHealthFacilityId() == null || !healthFacilityRepository
        .existsById(dto.getHealthFacilityId())) {
      throw new BaseException(400, "healthFacilityId is null or not exist");
    }
    if (dto.getSubjectIds() != null) {
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

    if (dto.getNgayKhaiGiang() != null
        && entity.getStatus().equals(CourseStatusEnum.HOAN_THANH.getValue())) {
      entity.setStatus(CourseStatusEnum.THOI_GIAN_DANG_KI.getValue());
    }
    if (dto.getNgayKhaiGiang() != null && entity.getStatus()
        .equals(CourseStatusEnum.DANG_HOC.getValue())) {
      throw new BaseException(403, "Không thể thay đổi khai giảng trong thời gian học");
    }
//    List<Authority> listRole = (List<Authority>) SecurityContextHolder.getContext().getAuthentication()
//        .getAuthorities();
//    boolean isAdmin = false;
//    for (Authority a : listRole) {
//      if (a.getAuthority().equals("ROLE_ADMIN")){
//        isAdmin = true;
//        break;
//      }
//    }
//    if (!isAdmin){
//      throw new BaseException(403, "Không đủ quyền");
//    }
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

  @Scheduled(cron = "0,20,40 * * * * *")
  public void update() {
    List<CourseEntity> allCourse = repository.search(new CourseDTO(),
        PageRequest.of(0, Integer.MAX_VALUE)).toList();
    List<CourseEntity> listSave = new ArrayList<>();
    for (CourseEntity e : allCourse) {
      if (e.getNgayKhaiGiang().before(new Date())) {
        e.setStatus(CourseStatusEnum.DANG_HOC.getValue());
        listSave.add(e);
      } else if (e.getNgayKetThuc().before(new Date())) {
        e.setStatus(CourseStatusEnum.HOAN_THANH.getValue());
        listSave.add(e);
      }

      // xóa lịch khi vào khai giảng mới
      if (e.getStatus().equals(CourseStatusEnum.HOAN_THANH.getValue()) &&
          e.getNgayKhaiGiang().after(new Date())) {
        e.setStatus(CourseStatusEnum.THOI_GIAN_DANG_KI.getValue());
        scheduleRepository.deleteAll(e.getSchedules());
        listSave.add(e);
      }
    }

    getRepository().saveAll(listSave);
  }
}
