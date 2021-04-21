package medical.education.dao.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

  private String code;

  /**
   * thời gian bắt đầu
   */
  private String startTime;

  /**
   * thời gian kết thúc
   */
  private String endTime;

  /**
   * giá
   */
  private Long price;

  /**
   * số tiết học
   */
  private Integer numberLesson;

  /**
   * số lượng đăng ký
   */
  private Integer numberRegister;

  /**
   * giới hạn đăng ký
   */
  private Integer limitRegister;

  /**
   * cơ sở y tế
   */
  @Column(name = "health_facility_id")
  private Long healthFacilityId;

  @OneToOne
  @JoinColumn(name = "health_facility_id",insertable = false,updatable = false)
  private HealthFacilityEntity healthFacility;

  /**
   * các môn trong chương trình
   */
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "course_subject",
      joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
  private List<SubjectEntity> subjects;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "course_register",
      joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
  private List<UserEntity> registers;

}
