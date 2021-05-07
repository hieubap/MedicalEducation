package medical.education.service;

import medical.education.dto.ScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import spring.backend.library.service.BaseService;

public interface ScheduleService extends BaseService<ScheduleDTO> {
  @PreAuthorize("hasAnyRole('ADMIN','STUDENT','TEACHER')")
  Page<ScheduleDTO> getSchedule();
}
