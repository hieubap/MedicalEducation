package medical.education.service;

import medical.education.dao.model.RoleEntity;
import medical.education.dao.repository.RoleRepository;
import medical.education.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.service.AbstractBaseService;

@Service
public class RoleServiceImpl extends AbstractBaseService<RoleEntity, RoleDTO, RoleRepository> implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  protected RoleRepository getRepository() {
    return roleRepository;
  }

  @Override
  public RoleEntity findRoleEntityById(Long id) {
    return roleRepository.findById(id).get();
  }
}
