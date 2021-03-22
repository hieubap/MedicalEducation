package medical.education.service;

import java.util.Map;
import medical.education.dao.model.UserEntity;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.LoginDTO;
import medical.education.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.backend.library.config.filter.JwtProvider;
import spring.backend.library.config.filter.JwtProvider.JwtTokenProperties;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;
import spring.backend.library.utils.DigestUtil;

@Service
public class UserServiceImpl extends
    AbstractBaseService<UserEntity, UserDTO, UserRepository> implements UserService {

  @Autowired
  private UserRepository repository;


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
    JwtTokenProperties jwts = JwtTokenProperties.builder()
        .id(userEntity.getId())
        .username(userEntity.getUsername())
        .fullName(userEntity.getFullName())
        .build();
    return jwtProvider.generateToken(jwts);
  }

  @Override
  protected void beforeSave(UserEntity entity, UserDTO dto) {
    super.beforeSave(entity, dto);
    if (repository.existsByUsername(dto.getUsername(), dto.getId())) {
      throw new BaseException(400, "username.is.exists");
    }
    entity.setPassword(DigestUtil.sha256Hex(dto.getPassword()));
  }


}
