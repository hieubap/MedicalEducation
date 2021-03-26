package medical.education.research.controller;

import medical.education.research.dto.StudentDTO;
import medical.education.research.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController extends BaseController<StudentDTO, StudentService> {
  @Autowired
  private StudentService studentService;

  @Override
  public StudentService getService() {
    return studentService;
  }

}
