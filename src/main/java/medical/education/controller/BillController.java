package medical.education.controller;

import java.time.LocalDate;
import medical.education.dto.BillDTO;
import medical.education.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.backend.library.controller.BaseController;
import spring.backend.library.dto.ResponseEntity;

@CrossOrigin
@RestController
@RequestMapping(value = "/bill")
public class BillController extends BaseController<BillDTO, BillService> {

  @Autowired
  private BillService billService;

  @Override
  public BillService getService() {
    return billService;
  }

  @GetMapping(value = "/dashboard")
  public ResponseEntity dashboard(
      @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
      @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
      @PageableDefault(size = 200, sort = "year", direction = Direction.DESC) Pageable pageable,
      Long userId){
    return response(billService.chart(from, to,userId,pageable));
  }

  @GetMapping(value = "/get-order")
  public ResponseEntity order(Long storeId,
      @PageableDefault(size = 200, sort = "id", direction = Direction.DESC) Pageable pageable){
    return response(billService.getOrder(storeId,pageable));
  }
  @GetMapping(value = "/get-bill")
  public ResponseEntity getBill(Long storeId,
      @PageableDefault(size = 200, sort = "id", direction = Direction.DESC) Pageable pageable){
    return response(billService.getBill(storeId,pageable));
  }


  @PutMapping(value = "/confirm/{id}")
  public ResponseEntity storeConfirm(@PathVariable(value = "id") Long id){
    return response(billService.storeConfirm(id));
  }

  @PutMapping(value = "/cancel/{id}")
  public ResponseEntity cancelBill(@PathVariable(value = "id") Long id) {
    return response(billService.cancel(id));
  }

  @PutMapping(value = "/delivered/{id}")
  public ResponseEntity deliveredBill(@PathVariable(value = "id") Long id){
    return response(billService.delivered(id));
  }
}
