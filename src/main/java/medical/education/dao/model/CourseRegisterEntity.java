package medical.education.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "course_register")
@Where(clause = "deleted=0")
public class CourseRegisterEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "student_id")
  private Long studentId;

  @Column(name = "course_id")
  private Long courseId;

  @OneToOne
  @JoinColumn(name = "student_id",insertable = false,updatable = false)
  private UserEntity student;

  @ManyToOne
  @JoinColumn(name = "course_id",insertable = false,updatable = false)
  private CourseEntity course;

  /**
   * 0 chờ xét duyệt
   * 1 thành công
   * 2 không thành công
   * 3 đã hoàn thành khóa học
   */
  private Short status;
}
