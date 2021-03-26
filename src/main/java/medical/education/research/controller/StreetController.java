package medical.education.research.controller;

import medical.education.research.dto.StreetDTO;
import medical.education.research.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RestController
@RequestMapping("/street")
public class StreetController extends BaseController<StreetDTO, StreetService> {
  @Autowired
  private StreetService streetService;

  @Override
  public StreetService getService() {
    return streetService;
  }
}
