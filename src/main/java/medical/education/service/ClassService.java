package medical.education.service;

import medical.education.dto.ClassDTO;
import spring.backend.library.dto.ResponseEntity;
import spring.backend.library.service.BaseService;

public interface ClassService extends BaseService<ClassDTO> {
  ResponseEntity approval(Long id);
  ResponseEntity cancel(Long id);

}
