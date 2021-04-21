package medical.education.controller;

import medical.education.dto.RegisterDTO;
import medical.education.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RequestMapping("/register")
@RestController
public class RegisterController extends BaseController<RegisterDTO, RegisterService> {
  @Autowired
  private RegisterService courseRegisterService;

  @Override
  public RegisterService getService() {
    return courseRegisterService;
  }

}
