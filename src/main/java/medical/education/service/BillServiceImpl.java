//package medical.education.service;
//
//import java.time.LocalDate;
//import java.util.Map;
//import medical.education.dao.model.ClassRegisterEntity;
//import medical.education.dao.repository.BillRepository;
//import medical.education.dao.repository.UserRepository;
//import medical.education.dto.BillDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import spring.backend.library.service.AbstractBaseService;
//
//@Service
//public class BillServiceImpl
//    extends AbstractBaseService<ClassRegisterEntity, BillDTO, BillRepository> implements BillService{
//  @Autowired
//  private BillRepository billRepository;
//
//  @Autowired
//  private UserRepository userRepository;
//
//  @Autowired
//  private UserServiceImpl userService;
//
//  @Override
//  protected BillRepository getRepository() {
//    return billRepository;
//  }
//
//  @Override
//  protected void specificMapToDTO(ClassRegisterEntity entity, BillDTO dto) {
//    super.specificMapToDTO(entity, dto);
//  }
//
//  @Override
//  protected void specificMapToEntity(BillDTO dto, ClassRegisterEntity entity) {
//    super.specificMapToEntity(dto, entity);
//  }
//
//  @Override
//  protected void beforeSave(ClassRegisterEntity entity, BillDTO dto) {
//    super.beforeSave(entity, dto);
//
//  }
//
//  @Override
//  protected void afterSave(ClassRegisterEntity entity, BillDTO dto) {
//    super.afterSave(entity, dto);
//
//  }
//
//  @Override
//  public BillDTO storeConfirm(Long id){
//    ClassRegisterEntity billEntity = billRepository.findById(id).get();
//
//    return mapToDTO(billEntity);
//  }
//
//  @Override
//  public BillDTO cancel(Long id) {
//    ClassRegisterEntity billEntity = billRepository.findById(id).get();
//
//    billRepository.save(billEntity);
//
//    return mapToDTO(billEntity);
//  }
//
//  @Override
//  public BillDTO delivered(Long id) {
//    ClassRegisterEntity billEntity = billRepository.findById(id).get();
//
//    billRepository.save(billEntity);
//
//    return mapToDTO(billEntity);
//  }
//
//  @Override
//  public Page<Map<Long,Long>> chart(LocalDate start, LocalDate end,
//      Long userId, Pageable pageable) {
//    Page<Map<Long,Long>> data = billRepository.dashboard(start, end,userId,pageable);
//
//    return data;
//  }
//
//  @Override
//  public Page<BillDTO> getOrder(Long storeId, Pageable pageable) {
//    return billRepository.getOrder(storeId,pageable).map(this::mapToDTO);
//  }
//
//  @Override
//  public Page<BillDTO> getBill(Long storeId, Pageable pageable) {
//    return billRepository.getBill(storeId,pageable).map(this::mapToDTO);
//  }
//}
