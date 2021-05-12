package medical.education.service;

import medical.education.dto.ResultDTO;
import spring.backend.library.service.BaseService;

public interface ResultService extends BaseService<ResultDTO> {
  void generateResult(Long classId);
  ResultDTO attendance(Long id);
}
