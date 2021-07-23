package medical.education.service;

import medical.education.dao.model.ScheduleEntity;
import medical.education.dao.repository.ScheduleRepository;
import medical.education.dao.repository.SessionScheduleRepository;
import medical.education.dto.SessionScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import medical.education.dao.model.SessionScheduleEntity;
import spring.backend.library.service.AbstractBaseService;

import java.util.List;

@Service
public class SessionScheduleServiceImpl extends AbstractBaseService<SessionScheduleEntity, SessionScheduleDTO, SessionScheduleRepository>
implements SessionScheduleService{
    @Autowired
    private SessionScheduleRepository sessionScheduleRepository;

    @Override
    protected SessionScheduleRepository getRepository() {
        return sessionScheduleRepository;
    }

    @Override
    protected void beforeSave(SessionScheduleEntity entity, SessionScheduleDTO dto) {
        super.beforeSave(entity, dto);
        if(entity.getId() == null){
            if(getRepository().existsByStartTimeAndEndTime(entity.getStartTime(),entity.getEndTime()))
                throw new BaseException(410,"Kíp học đã tồn tại");
        }
        else{
            if(getRepository().existByIdAndTime(entity))
                throw new BaseException(410,"Kíp học cập nhật đã tồn tại. Bị trùng kíp");
        }
    }

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    protected void beforeDelete(Long id) {
        super.beforeDelete(id);
        List<ScheduleEntity> schedules = scheduleRepository.findBySessionId(id);
        scheduleRepository.deleteAll(schedules);
    }
}
