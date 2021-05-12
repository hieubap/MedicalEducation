package medical.education.dao.repository;

import medical.education.dao.model.ResultEntity;
import medical.education.dto.ResultDTO;
import org.springframework.stereotype.Repository;
import spring.backend.library.dao.repository.BaseRepository;

@Repository
public interface ResultRepository extends BaseRepository<ResultEntity, ResultDTO,Long> {

}
