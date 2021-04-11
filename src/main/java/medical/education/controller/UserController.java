package medical.education.controller;

import medical.education.dto.LoginDTO;
import medical.education.dto.UserDTO;
import medical.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;
import spring.backend.library.dto.ResponseEntity;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController extends BaseController<UserDTO, UserService> {

  @Autowired
  private UserService service;

  @Override
  public UserService getService() {
    return service;
  }

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody LoginDTO dto) {
    return response(service.validateLogin(dto));
  }

  @PostMapping("/register")
  public ResponseEntity register(@RequestBody UserDTO userDTO){
    return response(getService().register(userDTO));
  }

  @PutMapping("/change-password")
  public ResponseEntity changePassword(@RequestBody UserDTO userDTO){
    return response(getService().changePassword(userDTO));
  }

}
