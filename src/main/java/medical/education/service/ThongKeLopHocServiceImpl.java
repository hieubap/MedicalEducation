package medical.education.service;

import medical.education.dao.model.ThongKeLopHocEntity;
import medical.education.dao.repository.ThongKeLopHocRepository;
import medical.education.dto.ThongKeLopHocDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.service.AbstractBaseService;

@Service
public class ThongKeLopHocServiceImpl extends
    AbstractBaseService<ThongKeLopHocEntity, ThongKeLopHocDTO, ThongKeLopHocRepository> implements
    ThongKeLopHocService {

  @Autowired
  private ThongKeLopHocRepository repository;

  @Override
  protected ThongKeLopHocRepository getRepository() {
    return repository;
  }
}
