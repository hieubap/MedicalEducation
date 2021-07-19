package medical.education.service;

import medical.education.dto.ResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.backend.library.service.BaseService;

import java.util.List;

public interface ResultService extends BaseService<ResultDTO> {
//  void generateResultsForStudent(Long classId,Long studentId,Long registerId);

  // teacher attendance
  ResultDTO attendance(Long id);

  // student get result
  Page<ResultDTO> getResult(ResultDTO searchDTO,Pageable page);

  ResultDTO enterPoint(ResultDTO resultDTO);

  List<ResultDTO> enterListPoint(List<ResultDTO> resultDTO);

  List<ResultDTO> findAllByCourseIdAndSubjectId(Long courseId,Long subjectId);
}
