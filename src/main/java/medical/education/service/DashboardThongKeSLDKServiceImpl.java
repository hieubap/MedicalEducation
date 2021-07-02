package medical.education.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import medical.education.dao.model.DashboardThongKeSLDKEntity;
import medical.education.dao.repository.DashboardThongKeSLDKRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardThongKeSLDKServiceImpl implements DashboardThongKeSLDKService {

    @Autowired
    private DashboardThongKeSLDKRepository repository;

    @Override
    public List<DashboardThongKeSLDKEntity> thongKeSoLuongDangKy(Integer namKhaoSat) {
        List<DashboardThongKeSLDKEntity> dashboardTk = new ArrayList<>();

        List<DashboardThongKeSLDKEntity> dashboard = repository.thongKeTheoNam(namKhaoSat);

        int[] a = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (DashboardThongKeSLDKEntity e : dashboard) {
            a[e.getId() - 1] = 1;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 0) {
                DashboardThongKeSLDKEntity entity = new DashboardThongKeSLDKEntity();
                entity.setId(i + 1);
                entity.setMonth(i + 1);
                entity.setYear(Calendar.getInstance().get(Calendar.YEAR));
                entity.setTotal(0l);
                dashboardTk.add(entity);
            } else {
                for (DashboardThongKeSLDKEntity e : dashboard) {
                    if (e.getMonth() == i + 1) {
                        dashboardTk.add(e);
                    }
                }
            }
        }
        return dashboardTk;
    }
}
