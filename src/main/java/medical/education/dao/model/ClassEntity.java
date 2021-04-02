package medical.education.dao.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "class")
@Where(clause = "deleted=0")
public class ClassEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 1: chờ đăng kí lớp
   * 2: đã đăng kí lớp
   * 3: xác nhận
   * 4: hủy lớp
   */
  private Short status;

  /**
   * mã lớp
   */
  private String code;

  @Column(name = "subject_id")
  private Long subjectId;

  @OneToOne
  @JoinColumn(name = "subject_id",updatable = false,insertable = false)
  private SubjectEntity subject;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  /**
   * t2: 2
   *
   */
  private Short day;

  /**
   * số lượng đăng ký
   */
  private Long numberRegister;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "class_register",
      joinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
  private List<UserEntity> register;
}
