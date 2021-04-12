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
import medical.education.enums.ClassRegisterEnum;
import org.hibernate.annotations.Where;
import spring.backend.library.dao.model.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted = 0")
@Entity
@Table(name = "class_register")
public class ClassRegisterEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "class_id")
  private Long classId;

  @Column(name = "student_id")
  private Long studentId;

  @ManyToOne
  @JoinColumn(name = "class_id",updatable = false,insertable = false)
  private ClassEntity classInfo;

  @OneToOne
  @JoinColumn(name = "student_id",insertable = false,updatable = false)
  private UserEntity student;

  /**
   * 0: thành công
   * 1: lớp bị hủy
   */
  private ClassRegisterEnum status;
}
