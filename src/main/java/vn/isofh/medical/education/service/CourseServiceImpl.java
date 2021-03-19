package vn.isofh.medical.education.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.isofh.common.service.AbstractBaseService;
import vn.isofh.medical.education.dao.model.CourseEntity;
import vn.isofh.medical.education.dao.model.SubjectEntity;
import vn.isofh.medical.education.dao.repository.CourseRepository;
import vn.isofh.medical.education.dto.CourseDTO;
import vn.isofh.medical.education.dto.SubjectDTO;
import vn.isofh.medical.education.exception.SubjectException.SubjectNotExist;

@Service
public class CourseServiceImpl extends
    AbstractBaseService<CourseEntity, CourseDTO, CourseRepository> implements CourseService {

  @Autowired
  private CourseRepository repository;

  @Autowired
  private SubjectService subjectService;

  @Override
  protected CourseRepository getRepository() {
    return repository;
  }

  @Override
  protected void specificMapToEntity(CourseDTO dto, CourseEntity entity) {
    super.specificMapToEntity(dto, entity);
    List<SubjectEntity> entities = new ArrayList<>();
    if (dto.getSubjects() != null) {

      for (SubjectDTO d : dto.getSubjects()) {
        if (d == null) {
          assert d != null;
          throw new SubjectNotExist(d.getId());
        }
        entities.add(subjectService.findEntityByid(d.getId()));
      }
    }

    entity.setSubjects(entities);
  }

  @Override
  protected void specificMapToDTO(CourseEntity entity, CourseDTO dto) {
    super.specificMapToDTO(entity, dto);
    List<SubjectDTO> dtos = new ArrayList<>();
    for (SubjectEntity e : entity.getSubjects()) {
      dtos.add(subjectService.findById(e.getId()));
    }

    dto.setSubjects(dtos);
  }
}
