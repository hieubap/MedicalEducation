package medical.education.dao.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import spring.backend.library.dao.model.BaseEntity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "session_schedule")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted = 0")
public class SessionScheduleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Time startTime;

    private Time endTime;
}
