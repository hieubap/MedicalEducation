package medical.education.service;

import java.util.ArrayList;
import java.util.List;
import medical.education.dao.model.CourseEntity;
import medical.education.dao.model.SubjectEntity;
import medical.education.dao.repository.CourseRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.CourseDTO;
import medical.education.dto.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
  private SubjectRepository subjectRepository;

  @Override
  protected CourseRepository getRepository() {
    return repository;
  }

  @Override
  protected void beforeSave(CourseEntity entity, CourseDTO dto) {
    super.beforeSave(entity, dto);
    if (dto.getName() == null) {
      throw new BaseException(400, "name is not null");
    }
    if (dto.getThoiGianHoc() == null) {
      throw new BaseException(400, "thoiGianHoc is not null");
    }
    if (dto.getPrice() == null) {
      throw new BaseException(400, "price is not null");
    }
  }

  @Override
  protected void specificMapToEntity(CourseDTO dto, CourseEntity entity) {
    super.specificMapToEntity(dto, entity);
    List<SubjectEntity> entities = new ArrayList<>();
    if (dto.getSubjectIds() != null) {
      for (Long d : dto.getSubjectIds()) {
        if (!subjectRepository.existsById(d)) {
          throw new BaseException(400, "id subject " + d + " is not exist");
        }
        entities.add(subjectService.findById(d));
      }
    }
    entity.setSubjects(entities);
  }

  @Override
  protected void specificMapToDTO(CourseEntity entity, CourseDTO dto) {
    super.specificMapToDTO(entity, dto);
    List<SubjectDTO> dtos = new ArrayList<>();
    for (SubjectEntity e : entity.getSubjects()) {
      dtos.add(subjectService.findDTO(e.getId()));
    }

    dto.setSubjects(dtos);
  }
}
