package medical.education.dao.repository;

import medical.education.dao.model.RoleEntity;
import medical.education.dto.RoleDTO;
import org.springframework.data.jpa.repository.Query;
import spring.backend.library.dao.repository.BaseRepository;

public interface RoleRepository extends BaseRepository<RoleEntity, RoleDTO,Long> {
  @Query("select e from RoleEntity e where e.name = :#{#name} or :#{#name} is null")
  RoleEntity findByName(String name);
}
