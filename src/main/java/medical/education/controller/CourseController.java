package medical.education.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import spring.backend.library.controller.BaseController;
import medical.education.dto.CourseDTO;
import medical.education.service.CourseService;
import spring.backend.library.dto.ResponseEntity;

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

    @GetMapping("/get-points")
    public ResponseEntity getStudentBySubject(@Param("courseId") Long courseId,
                                              @Param("subjectId") Long subjectId) {
        return response(getService().listPointBySubject(courseId, subjectId));
    }

}
