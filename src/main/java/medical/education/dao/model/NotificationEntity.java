package medical.education.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "notification")
@Where(clause = "deleted = 0")
public class NotificationEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

  /** 1: chưa đọc
   *  2: đã đọc
   */
  private Short isRead = 1;

  @Column(name = "owner_id")
  private Long ownerId;

  @ManyToOne
  @JoinColumn(name = "owner_id",updatable = false,insertable = false)
  private UserEntity owner;
}
