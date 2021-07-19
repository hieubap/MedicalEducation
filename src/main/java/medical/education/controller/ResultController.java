package medical.education.controller;

import medical.education.dto.ResultDTO;
import medical.education.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import spring.backend.library.controller.BaseController;
import spring.backend.library.dto.ResponseEntity;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/result")
public class ResultController extends BaseController<ResultDTO, ResultService> {

    @Autowired
    private ResultService resultService;

    @Override
    public ResultService getService() {
        return resultService;
    }

    @PutMapping("/attendance/{id}")
    public ResponseEntity attendance(@PathVariable("id") Long id) {
        return response(getService().attendance(id));
    }

    @GetMapping("/get-result")
    public ResponseEntity getResult(ResultDTO searchDTO, Pageable page) {
        return response(getService().getResult(searchDTO, page));
    }

    @PutMapping("/enter-point")
    public ResponseEntity enterPoint(@RequestBody ResultDTO resultDTO) {
        return response(getService().enterPoint(resultDTO));
    }

    @PostMapping("/enter-list-point")
    public ResponseEntity enterListPoint(@RequestBody List<ResultDTO> resultDTO) {
        return response(getService().enterListPoint(resultDTO));
    }
}
