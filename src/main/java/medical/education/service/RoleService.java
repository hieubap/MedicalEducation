package medical.education.service;

import medical.education.dao.model.RoleEntity;
import medical.education.dto.RoleDTO;
import spring.backend.library.service.BaseService;

public interface RoleService extends BaseService<RoleDTO> {
  RoleEntity findRoleEntityById(Long id);

}
