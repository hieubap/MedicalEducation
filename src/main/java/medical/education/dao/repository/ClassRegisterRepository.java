package medical.education.dao.repository;

import medical.education.dao.model.ClassRegisterEntity;
import medical.education.dto.ClassRegisterDTO;
import spring.backend.library.dao.repository.BaseRepository;

public interface ClassRegisterRepository extends
    BaseRepository<ClassRegisterEntity, ClassRegisterDTO, Long> {

}
