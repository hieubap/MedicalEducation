package medical.education.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;
import medical.education.dto.CourseDTO;
import medical.education.service.CourseService;

@CrossOrigin
@RestController
@RequestMapping("/courses")
public class CourseController extends BaseController<CourseDTO, CourseService> {

  @Autowired
  private CourseService service;

  @Override
  public CourseService getService() {
    return service;
  }
}
