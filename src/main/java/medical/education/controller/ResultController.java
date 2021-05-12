package medical.education.controller;

import medical.education.dto.ResultDTO;
import medical.education.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;
import spring.backend.library.dto.ResponseEntity;

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

  @PutMapping("/attendance/{id}")
  public ResponseEntity enterPoint(@PathVariable("id") Long id){
    return response(getService().attendance(id));
  }
}
