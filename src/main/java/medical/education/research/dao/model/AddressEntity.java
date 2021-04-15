package medical.education.research.dao.model;

import javax.persistence.Column;
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

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class AddressEntity extends BaseEntity {

  @Id
  @GeneratedValue(generator = "address_generator")
  @SequenceGenerator(name = "address_generator", sequenceName = "address_sq", initialValue = 1)
  private Long id;

  /**
   * home | work | temp | old | billing - purpose of this address
   */
  private Short purpose;

  /**
   * postal | physical | both
   */
  private Short type;

  /**
   * home number or detail
   */
  private String text;

  /**
   * line
   */
  @Column(name = "street_id")
  private Long streetId;

  @ManyToOne
  @JoinColumn(name = "street_id", updatable = false, insertable = false)
  private StreetEntity street;

  /**
   * city or district
   */
  @Column(name = "city_id")
  private Long cityId;

  @ManyToOne
  @JoinColumn(name = "city_id", insertable = false, updatable = false)
  private CityEntity city;

  /**
   * provincial
   */
  @Column(name = "provincial_id")
  private Long provincialId;

  @ManyToOne
  @JoinColumn(name = "provincial_id", updatable = false, insertable = false)
  private ProvincialEntity state;

  private String postalCode;

  private String country;
}
