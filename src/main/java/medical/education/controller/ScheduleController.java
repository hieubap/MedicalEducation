package medical.education.controller;

import medical.education.dto.ScheduleDTO;
import medical.education.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

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
}
