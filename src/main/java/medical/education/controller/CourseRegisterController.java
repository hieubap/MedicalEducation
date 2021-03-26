package medical.education.controller;

import medical.education.dto.CourseRegisterDTO;
import medical.education.service.CourseRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RequestMapping("/course-register")
@RestController
public class CourseRegisterController extends BaseController<CourseRegisterDTO, CourseRegisterService> {
  @Autowired
  private CourseRegisterService courseRegisterService;

  @Override
  public CourseRegisterService getService() {
    return courseRegisterService;
  }

}
