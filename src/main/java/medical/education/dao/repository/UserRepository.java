package medical.education.dao.repository;

import java.util.Optional;
import medical.education.dao.model.UserEntity;
import medical.education.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.backend.library.dao.repository.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, UserDTO, Long> {

  @Query("select case when count(e) > 0 then true else false end from UserEntity e"
      + " where e.username = :username")
  boolean existsByUsername(String username);

  Optional<UserEntity> findByUsername(String username);

  @Override
  @Query("select e from UserEntity e"
      + " where (e.id = :#{#dto.id} or :#{#dto.id} is null)"
      + " and (e.role = :#{#dto.role} or :#{#dto.role} is null)"
      + " and (e.idChange <> -1 or e.idChange is null)"
      + " and (e.username like :#{#dto.username} or :#{#dto.username} is null)"
      + " and (e.address like :#{#dto.address} or :#{#dto.address} is null)"
      + " and (e.fullName like :#{#dto.fullName} or :#{#dto.fullName} is null)"
      + " and (e.phoneNumber like :#{#dto.phoneNumber} or :#{#dto.phoneNumber} is null)"
      + " and (e.email like :#{#dto.email} or :#{#dto.email} is null)")
  Page<UserEntity> search(UserDTO dto, Pageable pageable);
}
