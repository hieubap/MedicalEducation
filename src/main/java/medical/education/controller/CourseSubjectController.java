package medical.education.controller;

import medical.education.dto.CourseSubjectDTO;
import medical.education.service.CourseSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RestController
@RequestMapping("/course-subject")
public class CourseSubjectController extends BaseController<CourseSubjectDTO, CourseSubjectService> {
  @Autowired
  private CourseSubjectService courseSubjectService;

  @Override
  public CourseSubjectService getService() {
    return courseSubjectService;
  }

}
