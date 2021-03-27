package medical.education.controller;

import medical.education.dto.StudyProcessDTO;
import medical.education.service.StudyProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RestController
@RequestMapping("/study-process")
public class StudyProcessController extends BaseController<StudyProcessDTO, StudyProcessService> {
  @Autowired
  private StudyProcessService studyProcessService;

  @Override
  public StudyProcessService getService() {
    return studyProcessService;
  }
}
