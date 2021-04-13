package medical.education.service;

import com.google.common.base.Strings;
import io.jsonwebtoken.Jwts;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import medical.education.dao.model.RoleEntity;
import medical.education.dao.model.UserEntity;
import medical.education.dao.repository.UserRepository;
import medical.education.dto.LoginDTO;
import medical.education.dto.UserDTO;
import medical.education.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import spring.backend.library.config.filter.JwtProvider;
import spring.backend.library.config.filter.JwtProvider.JwtTokenProperties;
import spring.backend.library.config.userdetail.UserDetail;
import spring.backend.library.dto.ResponseEntity;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;
import spring.backend.library.storage.StorageService;
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

  @Autowired
  private StorageService storageService;

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

    String role = "";
    if (userEntity.getRoleEntity() != null) {
      role = userEntity.getRoleEntity().getName();
    }

    if (role != null) {
      roles.add("ROLE_" + role);
    }

    JwtTokenProperties jwts = JwtTokenProperties.builder()
        .id(userEntity.getId())
        .username(userEntity.getUsername())
        .fullName(userEntity.getFullName())
        .role(roles.get(0))
        .privileges(roles)
        .build();
    return jwtProvider.generateToken(jwts);
  }

  @Override
  protected void beforeSave(UserEntity entity, UserDTO dto) {
    super.beforeSave(entity, dto);
    if ((dto.getUsername() == null || repository.existsByUsername(dto.getUsername()))
        && dto.getPasswordChange() == null) {
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
      RoleEntity roleEntity = roleService
          .findRoleEntityById(Long.valueOf(dto.getRole().toString()));

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
  public ResponseEntity changePassword(UserDTO userDTO) {
    UserDTO user = getCurrentUser();
    if (!DigestUtil.sha256Hex(userDTO.getPassword()).equals(user.getPassword())) {
      throw new BaseException(400, "password not correct");
    }

    user.setPassword(userDTO.getPasswordChange());
    user.setPasswordChange("=))");

    save(getCurrentUserId(), user);

    return new ResponseEntity(user);
  }

  @Override
  public ResponseEntity register(UserDTO userDTO) {
    userDTO.setRole(RoleEnum.STUDENT);
    userDTO.setStatus((short) 0);
    save(userDTO);

    return new ResponseEntity(userDTO);
  }

  @Override
  public UserDTO getCurrentUser() {
//<<<<<<< HEAD
//    return repository.findById(getCurrentUserId()).map(this::mapToDTO).get();
//=======
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()
        || authentication instanceof AnonymousAuthenticationToken) {
      return null;
    }
    UserDetail userDetail = (UserDetail) authentication.getPrincipal();
    return mapToDTO(repository.findById(userDetail.getId()).get());
  }

  @Value("${application.security.config.tokenPrefix}")
  private String preFix;

  @Autowired
  private SecretKey secretKey;

  @Override
  public Long getCurrentUserId() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes()).getRequest();
    String token = request.getHeader("Authorization").replace(preFix, "");

    Long userId = Long.valueOf(Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token).getBody().get("userId").toString());
    return userId;
  }

  @Override
  @PreAuthorize("hasAnyRole('ADMIN')")
  public Page<UserDTO> search(UserDTO dto, Pageable pageable) {
    return super.search(dto, pageable);
  }

  private final String[] ALLOW_EXTENSIONS = new String[]{"jpg", "png"};

  @Override
  public String uploadAvatar(MultipartFile file) {
    storageService.save(file);
    return "uploads" + "/" + file.getOriginalFilename();
  }

}
