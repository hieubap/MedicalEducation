package medical.education.controller;

import medical.education.dto.RoleDTO;
import medical.education.service.RoleService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController<RoleDTO, RoleService> {

  @Autowired
  private RoleService roleService;

  @Override
  public RoleService getService() {
    return roleService;
  }
}
