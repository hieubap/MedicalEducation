package medical.education.research.dao.model;

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
import spring.backend.library.dao.model.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "street")
public class StreetEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 20)
  private String name;

  @Column(name = "city_id")
  private Long cityId;

  @ManyToOne
  @JoinColumn(name = "city_id", insertable = false, updatable = false)
  private CityEntity city;

}
