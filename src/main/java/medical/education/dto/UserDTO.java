package medical.education.dto;

import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

  private Long role;

  private Long age;

  private Long gender;

  private String address;

  private String email;

  private String phoneNumber;

}
