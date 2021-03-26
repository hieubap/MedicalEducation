package medical.education.research.dao.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.backend.library.dao.model.BaseEntity;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
public class StudentEntity extends BaseEntity {
  @Id
  @GeneratedValue(generator = "students_generator")
  @SequenceGenerator(name = "students_generator", sequenceName = "students_sq", initialValue = 1)
  private Long id;

  private Boolean active;

  @ManyToOne
  @JoinColumn(name = "name_id")
  private NameEntity name;

  @ManyToOne
  @JoinColumn(name = "telecom_id")
  private ContactPointEntity telecom;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private AddressEntity address;

  /**
   * {@link medical.education.research.enums.GenderEnum}
   */
  private Short gender;

  private LocalDate birthDate;

  @ManyToOne
  @JoinColumn(name = "attachment_id")
  private AttachmentEntity photo;

  private String communication;

}
