package medical.education.service;

import medical.education.dto.ScheduleDTO;
import medical.education.dto.SubjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import spring.backend.library.service.BaseService;

public interface ScheduleService extends BaseService<ScheduleDTO> {
  @PreAuthorize("hasAnyRole('ADMIN','STUDENT','TEACHER')")
  Page<ScheduleDTO> getSchedule();

  Page<ScheduleDTO> getListClass(ScheduleDTO dto, Pageable page);
}
