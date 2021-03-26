package medical.education.service;

import java.time.LocalDate;
import java.util.Map;
import medical.education.dto.BillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.backend.library.service.BaseService;


public interface BillService extends BaseService<BillDTO> {

  BillDTO storeConfirm(Long id);
  BillDTO cancel(Long id);
  BillDTO delivered(Long id);
  Page<Map<Long,Long>> chart(LocalDate start,LocalDate end,Long userId, Pageable pageable);
  Page<BillDTO> getOrder(Long storeId,Pageable pageable);
  Page<BillDTO> getBill(Long storeId,Pageable pageable);
}
