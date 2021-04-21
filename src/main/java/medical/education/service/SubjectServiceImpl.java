package medical.education.service;

import com.google.common.base.Strings;
import java.text.Normalizer;
import medical.education.dao.model.SubjectEntity;
import medical.education.dao.repository.SubjectRepository;
import medical.education.dto.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class SubjectServiceImpl extends
    AbstractBaseService<SubjectEntity, SubjectDTO, SubjectRepository> implements SubjectService {

  @Autowired
  private SubjectRepository repository;

  @Override
  protected SubjectRepository getRepository() {
    return repository;
  }

  @Override
  @PreAuthorize("hasAnyRole('ADMIN')")
  protected void beforeSave(SubjectEntity entity, SubjectDTO dto) {
    super.beforeSave(entity, dto);

    if (Strings.isNullOrEmpty(dto.getName())) {
      throw new BaseException("name is null");
    }
    if (Strings.isNullOrEmpty(dto.getType())) {
      throw new BaseException("type is null");
    }

    if (Strings.isNullOrEmpty(dto.getCode())) {
      String s = Normalizer.normalize(dto.getName(), Normalizer.Form.NFD);
      s = s.toUpperCase().replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

      String shortName = s.charAt(0) + "" + s.charAt(1);

      entity.setCode(shortName + String.format("_%04d", repository.countAll()));
      entity.setShortName(shortName);
    }
  }
  @Override
  public Page<SubjectDTO> search(SubjectDTO dto, Pageable pageable) {
    if (dto.getName() != null) {
      dto.setName("%" + dto.getName().toLowerCase().trim().replaceAll(" ", "%") + "%");
    }
    if (dto.getCode() != null) {
      dto.setCode("%" + dto.getCode().toLowerCase().trim().replaceAll(" ", "%") + "%");
    }
    return super.search(dto, pageable);
  }

  //  @Scheduled(cron = "0,20,40 * * * * *")
//  public void schedule(){
//    List<SubjectEntity> list = (List<SubjectEntity>) repository.findAll();
//    for (SubjectEntity c : list){
//      String code = c.getShortName() + String.format("_%04d",c.getId());
//      c.setCode(code);
//    }
//    repository.saveAll(list);
//  }
}
