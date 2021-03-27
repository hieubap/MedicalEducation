package medical.education.service;

import medical.education.dto.StudyProcessDTO;
import spring.backend.library.service.BaseService;

public interface StudyProcessService extends BaseService<StudyProcessDTO> {
  void generateLearningRoute(Long classId);
}
