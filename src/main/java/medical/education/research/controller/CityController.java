package medical.education.research.controller;

import medical.education.research.dto.CityDTO;
import medical.education.research.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RestController
@RequestMapping("/city")
public class CityController extends BaseController<CityDTO, CityService> {
  @Autowired
  private CityService cityService;

  @Override
  public CityService getService() {
    return cityService;
  }
}
