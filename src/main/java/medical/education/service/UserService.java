package medical.education.service;

import java.util.Map;
import medical.education.dto.LoginDTO;
import medical.education.dto.UserDTO;
import spring.backend.library.dto.ResponseEntity;
import spring.backend.library.service.BaseService;

public interface UserService extends BaseService<UserDTO> {
    Map<String,Object> validateLogin(LoginDTO dto);
    ResponseEntity register(UserDTO userDTO);
}
