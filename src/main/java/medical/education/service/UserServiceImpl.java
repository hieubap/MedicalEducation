package medical.education.service;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import medical.education.enums.Gender;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import medical.education.dao.model.RoleEntity;
import medical.education.dao.model.UserEntity;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.LoginDTO;
import medical.education.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.backend.library.config.filter.JwtProvider;
import spring.backend.library.config.filter.JwtProvider.JwtTokenProperties;
import spring.backend.library.dto.ResponseEntity;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;
import spring.backend.library.utils.DigestUtil;

@Service

public class UserServiceImpl extends
    AbstractBaseService<UserEntity, UserDTO, UserRepository> implements UserService {

  @Autowired
  private UserRepository repository;

  @Autowired
  private RoleService roleService;

  @Autowired
  private JwtProvider jwtProvider;

  @Override
  protected UserRepository getRepository() {
    return repository;
  }

  @Override
  public Map<String, Object> validateLogin(LoginDTO dto) {
    if (dto.getPassword() == null || dto.getUsername() == null) {
      throw new BaseException(400, "password.or.username.is.null");
    }
    UserEntity userEntity = repository.findByUsername(dto.getUsername()).orElse(null);

    if (userEntity == null) {
      throw new BaseException(400, "username.not.exists");
    }

    if (!DigestUtil.sha256Hex(dto.getPassword()).equals(userEntity.getPassword())) {
      throw new BaseException(400, "password.invalid");
    }

    List<String> roles = new ArrayList<>();
    String role = null;

    if(userEntity.getRoleEntity() != null) {
       role = userEntity.getRoleEntity().getValue();
    }

    if(role != null){
      roles.add("ROLE_" + role);
    }

    JwtTokenProperties jwts = JwtTokenProperties.builder()
        .id(userEntity.getId())
        .username(userEntity.getUsername())
        .fullName(userEntity.getFullName())
        .privileges(roles)
        .build();
    return jwtProvider.generateToken(jwts);
  }

  @Override
  protected void beforeSave(UserEntity entity, UserDTO dto) {
    super.beforeSave(entity, dto);
    if (dto.getUsername() == null || repository.existsByUsername(dto.getUsername())) {
      throw new BaseException(400, "username is exists or null");
    }
    if (Strings.isNullOrEmpty(dto.getPassword())) {
      throw new BaseException(400, "password is empty or null");
    }
    entity.setPassword(DigestUtil.sha256Hex(dto.getPassword()));
  }

  @Override
  protected void specificMapToEntity(UserDTO dto, UserEntity entity) {
    super.specificMapToEntity(dto, entity);
    if (dto.getRole() != null) {
      RoleEntity roleEntity = roleService.findRoleEntityById(Long.valueOf(dto.getRole().toString()));

      if (roleEntity != null) {
        entity.setRoleEntity(roleEntity);
      }
    }
  }

//  @Override
//  protected void specificMapToDTO(UserEntity entity, UserDTO dto) {
//    super.specificMapToDTO(entity, dto);
//    if (entity.getRoleEntity() != null) {
//      dto.setRoleDTO(roleService.findById(entity.getRoleEntity().getId()));
//    }
//  }

  @Override
  public ResponseEntity register(UserDTO userDTO) {

    save(userDTO);

    return new ResponseEntity(userDTO);
  }

  @Override
  public UserDTO getCurrentUser() {
    return repository.findById(Long.valueOf("1")).map(this::mapToDTO).get();
  }

  @Override
  public Long getCurrentUserId() {
    return Long.valueOf("1");
  }

  @Override
  @PreAuthorize("hasAnyRole('TEARCHER', 'ADMIN', 'USER')")
  public Page<UserDTO> search(UserDTO dto, Pageable pageable) {
    return super.search(dto, pageable);
  }



}
