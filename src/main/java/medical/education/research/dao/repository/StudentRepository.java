package medical.education.research.dao.repository;

import medical.education.research.dao.model.StudentEntity;
import medical.education.research.dto.StudentDTO;
import spring.backend.library.dao.repository.BaseRepository;

public interface StudentRepository extends BaseRepository<StudentEntity, StudentDTO,Long> {

}
