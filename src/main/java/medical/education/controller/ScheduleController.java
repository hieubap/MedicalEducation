package medical.education.controller;

import medical.education.dto.ScheduleDTO;
import medical.education.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;
import spring.backend.library.dto.ResponseEntity;

@CrossOrigin
@RestController
@RequestMapping("/schedule")
public class ScheduleController extends BaseController<ScheduleDTO, ScheduleService> {
  @Autowired
  private ScheduleService scheduleService;

  @Override
  public ScheduleService getService() {
    return scheduleService;
  }

  @GetMapping("/get")
  public ResponseEntity get(){
    return response(getService().getSchedule());
  }

  @GetMapping("/get-class")
  public ResponseEntity getListClass(ScheduleDTO dto, Pageable page){
    return response(scheduleService.getListClass(dto,page));
  }
}
