package medical.education.dao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.backend.library.dao.model.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "learning")
public class StudyProcessEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "student_id")
  private UserEntity student;

  @OneToOne
  @JoinColumn(name = "room_id")
  private RoomEntity room;

  /**
   * 1 Tốt
   * 2 Khá
   * 3 Trung Bình
   * 4 Trượt
   * 5 đang học
   * 6 đình chỉ
   */
  private Short status;

  private String master;

  private Double midPoint;

  private Double EndPoint;

  private Double total;
}
