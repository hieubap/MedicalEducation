package medical.education.dao.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import medical.education.dto.RoleDTO;
import org.hibernate.annotations.Where;
import spring.backend.library.dao.model.BaseEntity;

@Entity
@Table(name = "user")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends BaseEntity {

  @Id
  @GeneratedValue(generator = "users_generator")
  @SequenceGenerator(name = "users_generator", sequenceName = "users_sq", initialValue = 1)
  private Long id;

  private String username;

  private String password;

  private String fullName;

  private String value;

  private Long age;

  private Long gender;

  private String address;

  private String email;

  private String phoneNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id")
  private RoleEntity roleEntity;

}
