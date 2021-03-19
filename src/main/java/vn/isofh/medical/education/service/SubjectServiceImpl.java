package vn.isofh.medical.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.isofh.common.service.AbstractBaseService;
import vn.isofh.medical.education.dao.model.SubjectEntity;
import vn.isofh.medical.education.dao.repository.SubjectRepository;
import vn.isofh.medical.education.dto.SubjectDTO;

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
  public SubjectEntity findEntityByid(Long id) {
    return repository.findById(id).orElse(null);
  }
}
