package medical.education.dao.model;


import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.backend.library.dao.model.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ClassPointEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean hasMidPoint;

    private Boolean hasEndPoint;

    private Long midtermScore;

    private Long finalScore;

    private Boolean isUsePoint;

    private Boolean isPass;

    @OneToMany(mappedBy = "classPointEntity" )
    private List<ClassPointEntity> classPointEntities;
}
