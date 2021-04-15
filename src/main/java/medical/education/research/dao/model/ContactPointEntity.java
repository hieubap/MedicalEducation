package medical.education.research.dao.model;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contact_point")
public class ContactPointEntity {

  @Id
  @GeneratedValue(generator = "contact_point_generator")
  @SequenceGenerator(name = "contact_point_generator", sequenceName = "contact_point_sq", initialValue = 1)
  private Long id;

  /**
   * phone | fax | email | pager | url | sms | other system
   */
  private Short systemType;

  /**
   * home | work | temp | old | mobile - purpose of this contact point use
   */
  private Short useType;

  /**
   * rank
   */
  private Short ranking;

  private ZonedDateTime startTime;

  private ZonedDateTime endTime;
}
