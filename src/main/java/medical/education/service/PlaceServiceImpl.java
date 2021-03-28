package medical.education.service;

import com.google.common.base.Strings;
import medical.education.dao.model.PlaceEntity;
import medical.education.dao.repository.PlaceRepository;
import medical.education.dto.PlaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class PlaceServiceImpl extends AbstractBaseService<PlaceEntity, PlaceDTO, PlaceRepository>
implements PlaceService{
  @Autowired
  private PlaceRepository placeRepository;

  @Override
  protected PlaceRepository getRepository() {
    return placeRepository;
  }

  @Override
  protected void beforeSave(PlaceEntity entity, PlaceDTO dto) {
    super.beforeSave(entity, dto);
    if(Strings.isNullOrEmpty(dto.getAddress())){
      throw new BaseException(400,"address is null or empty");
    }

  }
}
