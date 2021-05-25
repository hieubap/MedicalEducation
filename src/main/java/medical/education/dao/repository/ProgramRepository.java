package medical.education.dao.repository;


import medical.education.dao.model.ProgramEntity;
import medical.education.dto.ProgramDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.backend.library.dao.repository.BaseRepository;

public interface ProgramRepository extends BaseRepository<ProgramEntity, ProgramDTO, Long> {
//
//  @Override
//  Page<ProgramEntity> search(ProgramDTO dto, Pageable pageable);
}
