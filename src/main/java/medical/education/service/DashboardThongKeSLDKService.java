package medical.education.service;

import java.util.List;
import medical.education.dao.model.DashboardThongKeSLDKEntity;

public interface DashboardThongKeSLDKService {

    List<DashboardThongKeSLDKEntity> thongKeSoLuongDangKy(Integer namKhaoSat);
}
