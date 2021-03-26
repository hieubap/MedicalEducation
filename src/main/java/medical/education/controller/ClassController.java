package medical.education.controller;

import medical.education.dto.ClassDTO;
import medical.education.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RestController
@RequestMapping("/class")
public class ClassController extends BaseController<ClassDTO, ClassService> {
  @Autowired
  private ClassService classService;

  @Override
  public ClassService getService() {
    return classService;
  }
}
