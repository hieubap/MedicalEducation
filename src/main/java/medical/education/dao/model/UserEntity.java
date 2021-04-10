package medical.education.dao.model;

import java.util.List;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import medical.education.enums.Gender;
import medical.education.enums.GenderConverter;
import org.hibernate.annotations.Where;
import spring.backend.library.dao.model.BaseEntity;

@Entity
@Table(name = "users")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends BaseEntity {

  @Id
  @GeneratedValue(generator = "users_generator")
  @SequenceGenerator(name = "users_generator", sequenceName = "users_sq", initialValue = 1)
  private Long id;

  private String username;

  private String password;

  private String fullName;

  private String value;

  private Long age;

  private Short gender;

  private String address;

  private String email;

  private String phoneNumber;

  /**
   * 0: hoạt động
   * 1: khóa
   */
  private Short status;

  /**
   * 1: admin
   * 2: teacher
   * 3: student
   */
  private Short role;

  private Long roleId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id")
  private RoleEntity roleEntity;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "course_user",
      joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
  private List<CourseEntity> courses;

  @OneToMany(mappedBy = "student")
  private List<StudyProcessEntity> listStudyProcess;

}
