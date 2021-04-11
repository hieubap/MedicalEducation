package medical.education.service;

import medical.education.dao.model.RoleEntity;
import medical.education.dao.repository.RoleRepository;
import medical.education.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import spring.backend.library.service.AbstractBaseService;

@Service
@PreAuthorize("hasAnyRole('ADMIN')")
public class RoleServiceImpl extends AbstractBaseService<RoleEntity, RoleDTO, RoleRepository> implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  protected RoleRepository getRepository() {
    return roleRepository;
  }

  @Override
  @PreAuthorize("permitAll()")
  public RoleEntity findRoleEntityById(Long id) {
    return roleRepository.findById(id).orElse(null);
  }
}
