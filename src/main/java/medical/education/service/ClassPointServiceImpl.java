package medical.education.service;

import medical.education.dao.model.ClassPointEntity;
import medical.education.dao.repository.ClassPointRepository;
import medical.education.dto.ClassPointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.backend.library.exception.BaseException;
import spring.backend.library.service.AbstractBaseService;

@Service
public class ClassPointServiceImpl extends AbstractBaseService<ClassPointEntity, ClassPointDTO, ClassPointRepository> implements ClassPointService{

    @Autowired
    private ClassPointRepository repository;

    @Override
    protected ClassPointRepository getRepository() {
        return repository;
    }

    @Override
    protected void beforeSave(ClassPointEntity entity, ClassPointDTO dto) {
        if (dto.getIsUsePoint() && dto.getIsPass()) {
            throw new BaseException(1101, "Chỉ chọn 1 trong 2 option");
        }
        if (dto.getIsUsePoint() && !dto.getHasEndPoint() && dto.getHasMidPoint()) {
            throw new BaseException(1101, "Không thể đặt điểm giữa kỳ khi không có điểm cuối kỳ");
        }
        if (dto.getIsPass() && (dto.getHasEndPoint() || dto.getHasMidPoint())) {
            throw new BaseException(1101, "Môn học chỉ có thể có giá trị đạt hoặc không đạt");
        }
    }
}
