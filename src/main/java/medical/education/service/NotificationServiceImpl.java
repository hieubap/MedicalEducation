package medical.education.service;

import com.google.common.base.Strings;
import medical.education.dao.model.NotificationEntity;
import medical.education.dao.repository.NotificationRepository;
import medical.education.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class NotificationServiceImpl extends
    AbstractBaseService<NotificationEntity, NotificationDTO, NotificationRepository> implements NotificationService {
  @Autowired
  private NotificationRepository notificationRepository;

  @Override
  protected NotificationRepository getRepository() {
    return notificationRepository;
  }

  @Override
  protected void beforeSave(NotificationEntity entity, NotificationDTO dto) {
    super.beforeSave(entity, dto);
    if (Strings.isNullOrEmpty(dto.getContent()) &&
        dto.getOwnerId() == null){
      throw new BaseException(400,"content or ownerId is null or empty",null);
    }
    entity.setIsRead((short) 0);
  }

  @Scheduled(cron = "0 0 0,6,12,18 * * *")
  public void updateOnDay(){
    NotificationEntity notificationEntity = new NotificationEntity();
    notificationEntity.setOwnerId((long)2);
    notificationEntity.setAuditProperties(null,(long) 0, null, (long) 0);
    StringBuilder str = new StringBuilder();
    str.append("hôm nay có ")
//        .append(billRepository.numberBillOnDay(LocalDate.now()))
        .append(" đơn trong ngày");
    notificationEntity.setContent(str.toString());
    notificationRepository.save(notificationEntity);

  }

  @Override
  public NotificationDTO read(Long id) {
    NotificationEntity entity = notificationRepository.findById(id).get();
    entity.setIsRead((short)2);
    notificationRepository.save(entity);
    return mapToDTO(entity);
  }

  @Override
  public Integer countRead() {
    return notificationRepository.countRead();
  }
}