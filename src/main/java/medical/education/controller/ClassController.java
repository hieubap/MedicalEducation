package medical.education.controller;

import medical.education.dto.ClassDTO;
import medical.education.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;
import spring.backend.library.dto.ResponseEntity;

@RestController
@RequestMapping("/class")
public class ClassController extends BaseController<ClassDTO, ClassService> {

  @Autowired
  private ClassService classService;

  @Override
  public ClassService getService() {
    return classService;
  }

  @PutMapping("/approval/{id}")
  public ResponseEntity approval(@PathVariable Long id) {
    return response(classService.approval(id));
  }

  @PutMapping("/cancel")
  public ResponseEntity cancel(@RequestParam Long id) {
    return response(classService.cancel(id));
  }
}
