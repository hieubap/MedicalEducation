package medical.education.service;

import medical.education.dao.repository.SessionScheduleRepository;
import medical.education.dto.SessionScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.map.SessionScheduleEntity;
import spring.backend.library.service.AbstractBaseService;

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
}
