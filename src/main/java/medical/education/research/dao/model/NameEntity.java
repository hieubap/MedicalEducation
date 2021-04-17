package medical.education.research.dao.model;

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
@Table(name = "name")
public class NameEntity {

  @Id
  @GeneratedValue(generator = "names_generator")
  @SequenceGenerator(name = "names_generator", sequenceName = "names_sq", initialValue = 1)
  private Long id;

  /**
   * usual | official | temp | nickname | anonymous | old | maiden
   */
  private Short code;

  /**
   * fullname
   */
  private String text;

  private String family;

  /**
   * alway middle name
   */
  private String given;

  /**
   * ex: Mr.
   */
  private String prefix;

  private String suffix;


}
