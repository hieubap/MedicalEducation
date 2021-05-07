package medical.education.controller;

import medical.education.dto.ResultDTO;
import medical.education.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RestController
@RequestMapping("/result")
public class ResultController extends BaseController<ResultDTO, ResultService> {
  @Autowired
  private ResultService studyProcessService;

  @Override
  public ResultService getService() {
    return studyProcessService;
  }
}
