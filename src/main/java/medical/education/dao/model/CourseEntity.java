package medical.education.dao.model;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import spring.backend.library.dao.model.BaseEntity;

@Entity
@Table(name = "course")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class CourseEntity extends BaseEntity {

  @Id
  @GeneratedValue(generator = "course_generator")
  @SequenceGenerator(name = "course_generator", sequenceName = "course_sq", initialValue = 1)
  private Long id;

  private String name;

  private String value;

  private String thoiGianHoc;

  private String details;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "course_subject",
      joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
  private Collection<SubjectEntity> subjects;

}
