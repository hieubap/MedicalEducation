package medical.education.dao.repository;

import medical.education.dao.model.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import spring.backend.library.dao.repository.BaseRepository;
import medical.education.dto.CourseDTO;

public interface CourseRepository extends BaseRepository<CourseEntity, CourseDTO,Long> {
  @Override
  @Query("select e from CourseEntity e "
      + " where (lower(e.name) like :#{#dto.name} or :#{#dto.name} is null) "
      + " and (e.id = :#{#dto.id} or :#{#dto.id} is null) "
      + " and (lower(e.code) like :#{#dto.code} or :#{#dto.code} is null) "
      + " and (e.price = :#{#dto.price} or :#{#dto.price} is null) "
      + " and (e.status <> 3 or :#{#dto.scheduled} <> 1) "
      + " and (e.status = :#{#dto.status} or :#{#dto.status} is null) "
      + " and (e.semester = :#{#dto.semester} or :#{#dto.semester} is null) "
      + " and (e.numberLesson = :#{#dto.numberLesson} or :#{#dto.numberLesson} is null) "
      + " and (e.numberRegister = :#{#dto.numberRegister} or :#{#dto.numberRegister} is null) "
      + " and (e.limitRegister = :#{#dto.limitRegister} or :#{#dto.limitRegister} is null) "
      + " and (e.healthFacilityId = :#{#dto.healthFacilityId} or :#{#dto.healthFacilityId} is null) "
      + " and (lower(e.healthFacility.name) like :#{#dto.nameHealthFacility} or :#{#dto.nameHealthFacility} is null)"
  )
  Page<CourseEntity> search(CourseDTO dto, Pageable pageable);

  @Query("select case when count (e) > 0 then true else false end from CourseEntity e"
          + " where 1 = 1"
          + " and (e.name = :name)"
          + " and ( :id is null or e.id <> :id ) ")
  Boolean existsByNameAndId(String name, Long id);

  @Query("select case when count(e) > 0 then true else false end from CourseEntity e "
      + "where 1=1 "
      + "and (e.code = :#{#code})"
      + "and (e.id <> :#{#id})" )
  boolean existsByCode(String code,Long id);

  @Query("select case when count (e) > 0 then true else false end from CourseEntity e"
          + " where 1 = 1"
          + " and (e.code = :code)"
          + " and ( :id is null or e.id <> :id ) ")
  Boolean existsByCodeAndId(String code, Long id);

  boolean existsByCode(String code);

  Integer countByProgramId(Long programId);
}
