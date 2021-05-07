package medical.education.service;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import medical.education.dao.model.CourseEntity;
import medical.education.dao.model.ScheduleEntity;
import medical.education.dao.model.SubjectEntity;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.CourseSubjectRepository;
import medical.education.dao.repository.HealthFacilityRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.CourseDTO;
import medical.education.dto.ScheduleDTO;
import medical.education.dto.SubjectDTO;
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
  private CourseSubjectRepository courseSubjectRepository;

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
    if (Strings.isNullOrEmpty(dto.getStartTime())) {
      throw new BaseException(400, "startTime is null");
    }

    if (Strings.isNullOrEmpty(dto.getEndTime())) {
      throw new BaseException(400, "endTime is null");
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
    entity.setStatus((short)0);
  }

  @Override
  @Transactional
  protected void afterSave(CourseEntity entity, CourseDTO dto) {
    super.afterSave(entity, dto);

//    List<CourseSubjectEntity> listDelete = courseSubjectRepository.findByCourseId(entity.getId());
//    for (CourseSubjectEntity e : listDelete) {
//      courseSubjectRepository.deleteById(e.getId());
//    }
//
//    List<CourseSubjectEntity> listAdd = new ArrayList<>();
//
//    String[] ids = dto.getSubjectIds().substring(1, dto.getSubjectIds().length() - 1)
//        .split(",");
//    for (String idStr : ids) {
//      Long id = Long.valueOf(idStr);
//      CourseSubjectEntity e = new CourseSubjectEntity();
//      e.setCourseId(entity.getId());
//      e.setSubjectId(id);
//      listAdd.add(e);
//    }
//    courseSubjectRepository.saveAll(listAdd);
  }

  @Override
  protected void specificMapToEntity(CourseDTO dto, CourseEntity entity) {
//    super.specificMapToEntity(dto, entity);
//    List<SubjectEntity> entities = new ArrayList<>();
//    if (dto.getSubjectIds() != null) {
//      for (Long d : dto.getSubjectIds()) {
//        if (!subjectRepository.existsById(d)) {
//          throw new BaseException(400, "id subject " + d + " is not exist");
//        }
//        entities.add(subjectService.findById(d));
//      }
//    }
//    entity.setSubjects(entities);
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
  public Page<CourseDTO> search(CourseDTO dto, Pageable pageable) {
    if (dto.getName() != null) {
      dto.setName("%" + dto.getName().trim().replaceAll(" ", "%") + "%");
    }
    return super.search(dto, pageable);
  }

//  @Scheduled(cron = "0 * * * * *")
//  public void update(){
//    CourseDTO courseSearch = new CourseDTO();
//    courseSearch.setStatus((short)1);
//    List<CourseEntity> allCourse = (List<CourseEntity>) getRepository().search(courseSearch, PageRequest.of(0,999999));
//    for(CourseEntity e : allCourse){
//      if(e.start)
//    }

  }
}
