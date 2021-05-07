package medical.education.service;

import medical.education.dao.model.ScheduleEntity;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.PlaceRepository;
import medical.education.dao.repository.ScheduleRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
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
  private ScheduleRepository scheduleRepository;

  @Autowired
  private PlaceRepository placeRepository;

  @Autowired
  private SubjectRepository subjectRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private CourseService courseService;

  @Autowired
  private PlaceService placeService;

  @Override
  protected ScheduleRepository getRepository() {
    return scheduleRepository;
  }

  @Override
  @PreAuthorize("hasAnyRole('ADMIN')")
  protected void beforeSave(ScheduleEntity entity, ScheduleDTO dto) {
    super.beforeSave(entity, dto);
    if (dto.getDay() == null) {
      throw new BaseException(400, "day is null");
    }
    if (dto.getStartTime() == null) {
      throw new BaseException(400, "startTime is null");
    }

    if (dto.getEndTime() == null) {
      throw new BaseException(400, "endTime is null");
    }

    if (dto.getPlaceId() == null || !placeRepository.existsById(dto.getPlaceId())) {
      throw new BaseException(400, "placeId is null or not exist");
    }

    if (dto.getSubjectId() == null || !subjectRepository.existsById(dto.getSubjectId())) {
      throw new BaseException(400, "subjectId is null or not exist");
    }

    if (dto.getCourseId() == null || !courseRepository.existsById(dto.getCourseId())) {
      throw new BaseException(400, "courseId is null or not exist");
    }
  }

  @Override
  protected void afterSave(ScheduleEntity entity, ScheduleDTO dto) {
    super.afterSave(entity, dto);
    entity.setSubject(subjectRepository.findById(entity.getSubjectId()).get());
    entity.setPlace(placeRepository.findById(entity.getPlaceId()).get());
  }

  @Override
  public Page<ScheduleDTO> getSchedule() {
    ScheduleDTO scheduleSearch = new ScheduleDTO();
    scheduleSearch.setCourseId(userService.getCurrentUser().getCurrentCourseId());
    return scheduleService.search(scheduleSearch, PageRequest.of(0, 999999));
  }
}
