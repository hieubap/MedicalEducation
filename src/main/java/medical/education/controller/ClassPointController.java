package medical.education.controller;

import medical.education.dto.ClassPointDTO;
import medical.education.service.ClassPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;

@RestController
@RequestMapping(value = "/class-point")
public class ClassPointController extends BaseController<ClassPointDTO, ClassPointService> {

    @Autowired
    private ClassPointService service;

    @Override
    public ClassPointService getService() {
        return service;
    }
}
