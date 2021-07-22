package medical.education.controller;

import medical.education.dto.SessionScheduleDTO;
import medical.education.service.SessionScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RestController
@RequestMapping("/session-schedule")
public class SessionScheduleController extends BaseController<SessionScheduleDTO, SessionScheduleService> {
    @Autowired
    private SessionScheduleService sessionScheduleService;

    @Override
    public SessionScheduleService getService() {
        return sessionScheduleService;
    }
}
