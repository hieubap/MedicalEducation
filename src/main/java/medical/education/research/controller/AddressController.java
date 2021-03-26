package medical.education.research.controller;

import medical.education.research.dto.AddressDTO;
import medical.education.research.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RequestMapping("/address")
@RestController
public class AddressController extends BaseController<AddressDTO, AddressService> {
  @Autowired
  private AddressService addressService;

  @Override
  public AddressService getService() {
    return addressService;
  }
}
