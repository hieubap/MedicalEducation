package medical.education.research.controller;

import medical.education.research.dto.ProvincialDTO;
import medical.education.research.service.ProvincialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RestController
@RequestMapping("/provincial")
public class ProvincialController extends BaseController<ProvincialDTO, ProvincialService> {
  @Autowired
  private ProvincialService provincialService;

  @Override
  public ProvincialService getService() {
    return provincialService;
  }
}
