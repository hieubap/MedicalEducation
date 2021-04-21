package medical.education.service;

import medical.education.dao.model.ScheduleEntity;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.PlaceRepository;
import medical.education.dao.repository.ScheduleRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
@PreAuthorize("hasAnyRole('ADMIN')")
public class ScheduleServiceImpl extends
    AbstractBaseService<ScheduleEntity, ScheduleDTO, ScheduleRepository>
    implements ScheduleService {
  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private PlaceRepository placeRepository;

  @Autowired
  private SubjectRepository subjectRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Override
  protected ScheduleRepository getRepository() {
    return scheduleRepository;
  }

  @Override
  protected void beforeSave(ScheduleEntity entity, ScheduleDTO dto) {
    super.beforeSave(entity, dto);
    if(dto.getDay() == null){
      throw new BaseException(400,"day is null");
    }
    if(dto.getStartTime() == null)
      throw new BaseException(400,"startTime is null");

    if(dto.getEndTime() == null)
      throw new BaseException(400,"endTime is null");

    if(dto.getPlaceId() == null || !placeRepository.existsById(dto.getPlaceId()))
      throw new BaseException(400,"placeId is null or not exist");

    if(dto.getSubjectId() == null || !subjectRepository.existsById(dto.getSubjectId()))
      throw new BaseException(400,"subjectId is null or not exist");

    if(dto.getCourseId() == null || !courseRepository.existsById(dto.getCourseId()))
      throw new BaseException(400,"courseId is null or not exist");
  }
}
