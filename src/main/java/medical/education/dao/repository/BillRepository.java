package medical.education.dao.repository;


import java.time.LocalDate;
import java.util.Map;
import medical.education.dao.model.SubjectRegisterEntity;
import medical.education.dto.BillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import spring.backend.library.dao.repository.BaseRepository;

public interface BillRepository extends BaseRepository<SubjectRegisterEntity, BillDTO,Long> {
  @Query(value = "select * from ( select "
      + "       year(create_at) as year, "
      + "       month(create_at) as month, "
      + "       day(create_at) as day, "
      + "       count(day(create_at)) as soluong "
      + " from bill "
      + " where deleted = 0"
      + " and (buyer_id = :#{#userId} or :#{#userId} is null) "
      + " and (date(create_at) <= :#{#end} or :#{#end} is null) "
      + " and (date(create_at) >= :#{#start} or :#{#start} is null)"
      + " group by year(create_at),month(create_at), day(create_at) ) as tb ",
      countQuery = "select count(*) from ( select "
          + "       year(create_at) as year, "
          + "       month(create_at) as month, "
          + "       day(create_at) as day, "
          + "       count(day(create_at)) as soluong "
          + " from bill "
          + " where deleted = 0"
          + " and (buyer_id = :#{#userId} or :#{#userId} is null) "
          + " and (date(create_at) <= :#{#end} or :#{#end} is null) "
          + " and (date(create_at) >= :#{#start} or :#{#start} is null)"
          + " group by year(create_at),month(create_at), day(create_at) ) as tb "
      ,nativeQuery = true)
  Page<Map<Long,Long>> dashboard(LocalDate start,LocalDate end,Long userId,Pageable pageable);

  @Override
  @Query(value = "select e from SubjectRegisterEntity e"
      + " left join e.buyer buyer"
      + " where e.deleted = 0 "
      + " and (buyer.id = :#{#dto.buyerId} or :#{#dto.buyerId} is null )"
      + " and (e.id = :#{#dto.id} or :#{#dto.id} is null) "
      + " and (e.status = :#{#dto.status} or :#{#dto.status} is null)"

      , countQuery = "select count(e) from SubjectRegisterEntity e"
      + " left join e.buyer buyer"
      + " where e.deleted = 0 "
      + " and (buyer.id = :#{#dto.buyerId} or :#{#dto.buyerId} is null )"
      + " and (e.id = :#{#dto.id} or :#{#dto.id} is null) "
      + " and (e.status = :#{#dto.status} or :#{#dto.status} is null)")
  Page<SubjectRegisterEntity> search(BillDTO dto, Pageable page);

  @Query("select e from SubjectRegisterEntity e"
      + " where e.deleted = 0 "
      + "and e.status = 1 ")
  Page<SubjectRegisterEntity> getOrder(Long storeId, Pageable pageable);

  @Query("select e from SubjectRegisterEntity e"
      + " where e.deleted = 0 "
      + "and e.status <> 1 ")
  Page<SubjectRegisterEntity> getBill(Long storeId, Pageable pageable);

  @Override
  @Transactional
  @Modifying
  @Query(value = "update SubjectRegisterEntity e set e.deleted = 1 "
      + " where e.id = ?#{#id} ")
  void deleteById(Long id);

  @Query("select count(e) from SubjectRegisterEntity e"
      + " where e.deleted = 0 "
      + "and day(e.createAt) = day(:#{#date})")
  Integer numberBillOnDay(LocalDate date);
}
