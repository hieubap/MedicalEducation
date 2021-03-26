package medical.education.dto;

import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import medical.education.enums.Gender;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class UserDTO extends BaseDTO {

  private String username;

  private String password;

  private String fullName;

  private String value;

  private Long age;

  private Gender gender;

  private String address;

  private String email;

  private String phoneNumber;

  private Long roleId;

  private RoleDTO roleDTO;


}
