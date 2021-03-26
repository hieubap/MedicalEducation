package medical.education.research.dao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "qualification")
public class QualificationEntity {
  @Id
  @GeneratedValue(generator = "qualification_generator")
  @SequenceGenerator(name = "qualification_generator", sequenceName = "qualification_sq", initialValue = 1)
  private Long id;

  private String name;
}
