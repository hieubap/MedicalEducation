package medical.education.controller;

import medical.education.dto.SubjectDTO;
import medical.education.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@RestController
@RequestMapping("/subjects")
public class SubjectController extends BaseController<SubjectDTO, SubjectService> {

  @Autowired
  private SubjectService service;

  @Override
  public SubjectService getService() {
    return service;
  }
}
