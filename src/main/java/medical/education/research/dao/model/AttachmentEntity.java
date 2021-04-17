package medical.education.research.dao.model;

import java.time.ZonedDateTime;
import javax.persistence.Column;
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
@Table(name = "attachment")
public class AttachmentEntity {

  @Id
  @GeneratedValue(generator = "attachment_generator")
  @SequenceGenerator(name = "attachment_generator", sequenceName = "attachment_sq", initialValue = 1)
  private Long id;

  /**
   *
   */
  private Short contentType;

  /**
   * 1 vietnamese 2 english
   */
  private Short language;

  @Column(length = 1000)
  private String data;

  private String url;

  private Integer size;

  private String hash;

  private String title;
}
