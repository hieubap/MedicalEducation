package medical.education.dao.model;

import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "thong_ke_lop_hoc")
@Where(clause = "deleted=0")
public class ThongKeLopHocEntity extends BaseEntity {
  @Id
  private Long id;

  private Long limitRegister;

  private Short status;

  private Short day;

  private Long teacherId;

  private String time;

  private Long courseId;

  private Long placeId;

  private Long soLuong;

}
