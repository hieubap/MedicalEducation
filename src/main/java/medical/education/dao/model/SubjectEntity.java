package medical.education.dao.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import spring.backend.library.dao.model.BaseEntity;

@Entity
@Table(name = "subject")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class SubjectEntity extends BaseEntity {

  @Id
  @GeneratedValue(generator = "subject_generator")
  @SequenceGenerator(name = "subject_generator", sequenceName = "subject_sq", initialValue = 1)
  private Long id;

  @Column(nullable = false)
  private String value;

  @Column(nullable = false)
  private String name;

//  @Column(columnDefinition = "VARCHAR(60) CHECK (status in ('LT + BT','TH'))", nullable = false)
  private String type;

  @ManyToMany(mappedBy = "subjects")
  private List<CourseEntity> courseEntities;
}