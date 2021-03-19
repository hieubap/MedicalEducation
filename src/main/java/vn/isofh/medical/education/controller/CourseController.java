package vn.isofh.medical.education.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.isofh.common.controller.BaseController;
import vn.isofh.medical.education.dto.CourseDTO;
import vn.isofh.medical.education.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController extends BaseController<CourseDTO, CourseService> {

  @Autowired
  private CourseService service;

  @Override
  protected CourseService getService() {
    return service;
  }
}
