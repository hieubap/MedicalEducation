package vn.isofh.medical.education.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.isofh.common.controller.BaseController;
import vn.isofh.medical.education.dto.SubjectDTO;
import vn.isofh.medical.education.service.SubjectService;

@RestController
@RequestMapping("/subjects")
public class SubjectController extends BaseController<SubjectDTO, SubjectService> {

  @Autowired
  private SubjectService service;

  @Override
  protected SubjectService getService() {
    return service;
  }
}
