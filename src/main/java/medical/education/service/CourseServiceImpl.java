package medical.education.service;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import medical.education.dao.model.CourseEntity;
import medical.education.dao.model.SubjectEntity;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.CourseSubjectRepository;
import medical.education.dao.repository.HealthFacilityRepository;
import medical.education.dto.CourseDTO;
import medical.education.dto.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  private SubjectService subjectService;

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
  }

//  @Override
//  @Transactional
//  protected void afterSave(CourseEntity entity, CourseDTO dto) {
//    super.afterSave(entity, dto);
//
//    List<CourseSubjectEntity> listDelete = courseSubjectRepository.findByCourseId(entity.getId());
//    List<CourseSubjectEntity> listAdd = new ArrayList<>();
//
//    for (Long id : dto.getSubjectIds()) {
//      if (!courseSubjectRepository.exist(entity.getId(), id)) {
//        CourseSubjectEntity e = new CourseSubjectEntity();
//        e.setCourseId(entity.getId());
//        e.setSubjectId(id);
//        listAdd.add(e);
//      } else {
//        for (int i = 0; i < listDelete.size(); i++) {
//          if (listDelete.get(i).getSubjectId().equals(id)) {
//            listDelete.remove(i);
//            break;
//          }
//        }
//      }
//    }
////    courseSubjectRepository.saveAll(listAdd);
//    courseSubjectRepository.deleteAll(listDelete);
//  }

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
    if (entity.getMapAllProperties() && entity.getSubjectIds()!=null && entity.getSubjectIds().length() > 2) {
      String[] ids = entity.getSubjectIds().substring(1, entity.getSubjectIds().length() - 1)
          .split(",");
      List<SubjectDTO> dtos = new ArrayList<>();
      for (String e : ids) {
        dtos.add(subjectService.findById(Long.valueOf(e)));
      }
      dto.setListSubject(dtos);
    }
  }

  @Override
  public Page<CourseDTO> search(CourseDTO dto, Pageable pageable) {
    if (dto.getName() != null) {
      dto.setName("%" + dto.getName().trim().replaceAll(" ", "%") + "%");
    }
    return super.search(dto, pageable);
  }
}
