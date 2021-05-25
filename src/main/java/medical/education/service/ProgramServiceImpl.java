package medical.education.service;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import medical.education.dao.model.CourseEntity;
import medical.education.dao.model.ProgramEntity;
import medical.education.dao.model.ScheduleEntity;
import medical.education.dao.model.SubjectEntity;
import medical.education.dao.repository.ProgramRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.CourseDTO;
import medical.education.dto.ProgramDTO;
import medical.education.dto.ScheduleDTO;
import medical.education.dto.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.service.AbstractBaseService;

@Service
public class ProgramServiceImpl extends
    AbstractBaseService<ProgramEntity, ProgramDTO, ProgramRepository> implements ProgramService {

  @Autowired
  private ProgramRepository programRepository;

  @Autowired
  private SubjectRepository subjectRepository;

  @Autowired
  private SubjectService subjectService;

  @Override
  protected ProgramRepository getRepository() {
    return programRepository;
  }

  @Override
  protected void beforeSave(ProgramEntity entity, ProgramDTO dto) {
    super.beforeSave(entity, dto);
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
  }

  @Override
  protected void specificMapToEntity(ProgramDTO dto, ProgramEntity entity) {
    super.specificMapToEntity(dto, entity);
    List<SubjectEntity> subjectEntities = dto.getSubjectsIds().stream().map(e -> {
      return subjectRepository.findById(e).get();
    }).collect(Collectors.toList());
    entity.setSubjects(subjectEntities);
  }

  @Override
  protected void specificMapToDTO(ProgramEntity entity, ProgramDTO dto) {
    super.specificMapToDTO(entity, dto);
    if (entity.getSubjects() != null) {
      dto.setListSubjects(
          entity.getSubjects().stream().map(e -> subjectService.findById(e.getId())).collect(
              Collectors.toList()));
    }
  }
}
