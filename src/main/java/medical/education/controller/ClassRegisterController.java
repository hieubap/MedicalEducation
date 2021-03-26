package medical.education.controller;

import medical.education.dto.ClassRegisterDTO;
import medical.education.service.ClassRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RestController
@RequestMapping("class-register")
public class ClassRegisterController extends BaseController<ClassRegisterDTO, ClassRegisterService> {
  @Autowired
  private ClassRegisterService classRegisterService;

  @Override
  public ClassRegisterService getService() {
    return classRegisterService;
  }

}
