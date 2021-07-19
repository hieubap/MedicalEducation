package medical.education.service;

import java.util.List;
import medical.education.dto.RegisterDTO;
import spring.backend.library.service.BaseService;

public interface RegisterService extends BaseService<RegisterDTO> {
    List<RegisterDTO> findAllByCourseId(Long courseId);
}
