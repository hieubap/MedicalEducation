package medical.education.dao.model;

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
@Where(clause = "deleted = 0")
@Entity
@Table(name = "subject_register")
public class SubjectRegisterEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 1: chờ xếp lớp
   * 2: đã xác nhận
   * 3: hủy
   */
  private Short status;

  @OneToOne
  @JoinColumn(name = "course_id")
  private CourseEntity course;

  @ManyToOne
  @JoinColumn(name = "buyer_id")
  private UserEntity buyer;
}
