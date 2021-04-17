package medical.education.research.service;

import medical.education.research.dao.model.StudentEntity;
import medical.education.research.dao.repository.CityRepository;
import medical.education.research.dao.repository.ProvincialRepository;
import medical.education.research.dao.repository.StreetRepository;
import medical.education.research.dao.repository.StudentRepository;
import medical.education.research.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.service.AbstractBaseService;

@Service
public class StudentServiceImpl extends
    AbstractBaseService<StudentEntity, StudentDTO, StudentRepository>
    implements StudentService {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private StreetRepository streetRepository;

  @Autowired
  private CityRepository cityRepository;

  @Autowired
  private ProvincialRepository provincialRepository;

  @Override
  protected StudentRepository getRepository() {
    return studentRepository;
  }

  @Override
  protected void beforeSave(StudentEntity entity, StudentDTO dto) {
    super.beforeSave(entity, dto);
//    AddressDTO dto1 = addressService.save(dto.getAddress());
//    addressService.beforeSave(entity.getAddress(),dto.getAddress());
//    if (entity.getName() == null)
//      throw new BaseException(400,"name is null");
  }

}
