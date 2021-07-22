package medical.education.dao.repository;

import medical.education.dto.SessionScheduleDTO;
import org.springframework.data.jpa.repository.Query;
import spring.backend.library.dao.repository.BaseRepository;
import spring.backend.library.map.SessionScheduleEntity;

import java.sql.Time;

public interface SessionScheduleRepository
        extends BaseRepository<SessionScheduleEntity, SessionScheduleDTO, Long> {
    Boolean existsByStartTimeAndEndTime(Time startTime, Time endTime);

    @Query("select case when count(e) > 0 then true else false end from SessionScheduleEntity e "
            + "where 1=1 "
            + "and (e.id <> :#{#entity.id})"
            + "and (e.startTime = :#{#entity.startTime}) "
            + "and (e.endTime = :#{#entity.endTime})")
    Boolean existByIdAndTime(SessionScheduleEntity entity);

}
