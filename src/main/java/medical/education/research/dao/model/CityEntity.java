package medical.education.research.dao.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.backend.library.dao.model.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "city")
public class CityEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 20)
  private String name;

  @Column(name = "provincial_id")
  private Long provincialId;

  @ManyToOne
  @JoinColumn(name = "provincial_id", updatable = false, insertable = false)
  private ProvincialEntity provincial;

  @OneToMany(mappedBy = "city")
  private List<StreetEntity> streets;
}
