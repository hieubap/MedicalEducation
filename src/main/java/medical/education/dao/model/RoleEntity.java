package medical.education.dao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import spring.backend.library.dao.model.BaseEntity;

@Entity
@Table(name = "role")
@Where(clause = "deleted = 0")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity extends BaseEntity {

  @Id
  @GeneratedValue(generator = "role_generator")
  @SequenceGenerator(name = "role_generator", sequenceName = "role_sq", initialValue = 1)
  private Long id;

  private String name;

  private String value;

}
