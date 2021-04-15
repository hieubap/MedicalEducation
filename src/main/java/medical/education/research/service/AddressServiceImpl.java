package medical.education.research.service;

import medical.education.research.dao.model.AddressEntity;
import medical.education.research.dao.repository.AddressRepository;
import medical.education.research.dao.repository.CityRepository;
import medical.education.research.dao.repository.ProvincialRepository;
import medical.education.research.dao.repository.StreetRepository;
import medical.education.research.dto.AddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class AddressServiceImpl extends
    AbstractBaseService<AddressEntity, AddressDTO, AddressRepository>
    implements AddressService {

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private StreetRepository streetRepository;

  @Autowired
  private CityRepository cityRepository;

  @Autowired
  private ProvincialRepository provincialRepository;

  @Override
  protected AddressRepository getRepository() {
    return addressRepository;
  }

  @Override
  protected void beforeSave(AddressEntity entity, AddressDTO dto) {
    super.beforeSave(entity, dto);
    if (entity.getStreetId() == null) {
      throw new BaseException(400, "streetId is null");
    }
    if (entity.getCityId() == null) {
      throw new BaseException(400, "cityId is null");
    }
    if (entity.getProvincialId() == null) {
      throw new BaseException(400, "provincialId is null");
    }
    entity.setStreet(streetRepository.findById(dto.getStreetId()).get());
    entity.setCity(cityRepository.findById(dto.getCityId()).get());
    entity.setState(provincialRepository.findById(dto.getProvincialId()).get());
  }

  @Override
  protected void specificMapToDTO(AddressEntity entity, AddressDTO dto) {
    super.specificMapToDTO(entity, dto);

    StringBuilder str = new StringBuilder();
    str.append(entity.getStreet().getName())
        .append(" - ")
        .append(entity.getCity().getName())
        .append(" - ")
        .append(entity.getState().getName());
    dto.setName(str.toString());
  }
}
