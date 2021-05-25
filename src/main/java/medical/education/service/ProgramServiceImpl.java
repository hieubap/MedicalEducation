package medical.education.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import medical.education.dao.model.ProgramEntity;
import medical.education.dao.model.SubjectEntity;
import medical.education.dao.repository.ProgramRepository;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.ProgramDTO;
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
