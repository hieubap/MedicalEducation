package medical.education.dao.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import spring.backend.library.dao.model.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "course_subject")
@Where(clause = "deleted=0")
public class CourseSubjectEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "course_id")
  private Long courseId;

  @Column(name = "subject_id")
  private Long subjectId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "subject_id", insertable = false, updatable = false)
  private SubjectEntity subject;
}
