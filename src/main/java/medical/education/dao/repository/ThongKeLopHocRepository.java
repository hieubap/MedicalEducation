package medical.education.dao.repository;

import medical.education.dao.model.ThongKeLopHocEntity;
import medical.education.dto.ThongKeLopHocDTO;
import spring.backend.library.dao.repository.BaseRepository;

public interface ThongKeLopHocRepository extends
    BaseRepository<ThongKeLopHocEntity, ThongKeLopHocDTO, Long> {

}
