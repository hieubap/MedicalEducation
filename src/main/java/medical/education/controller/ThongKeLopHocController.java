package medical.education.controller;

import medical.education.dto.ThongKeLopHocDTO;
import medical.education.service.ThongKeLopHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RestController
@RequestMapping("/thong-ke-lop-hoc")
public class ThongKeLopHocController extends
    BaseController<ThongKeLopHocDTO, ThongKeLopHocService> {

  @Autowired
  private ThongKeLopHocService service;

  @Override
  public ThongKeLopHocService getService() {
    return service;
  }
}
