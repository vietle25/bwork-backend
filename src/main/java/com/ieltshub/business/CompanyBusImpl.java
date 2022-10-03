
package com.ieltshub.business;

import com.ieltshub.entity.User;
import com.ieltshub.exception.BusinessException;
import com.ieltshub.repository.SabbaticalRepository;
import com.ieltshub.repository.TimekeepingRecordRepository;
import com.ieltshub.repository.UserRepository;
import com.ieltshub.viewmodel.cache.UserInfo;
import com.ieltshub.viewmodel.company.DashboardFilter;
import com.ieltshub.viewmodel.company.DashboardModel;
import com.ieltshub.viewmodel.timekeeping.history.TimekeepingHistoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User bus
 *
 * @since 1.0
 */
@Service
public class CompanyBusImpl extends AbstractBusiness implements CompanyBus {

    @Autowired
    UserRepository userRepo;

    @Autowired
    TimekeepingRecordRepository timekeepingRecordRepo;

    @Autowired
    SabbaticalRepository sabbaticalRepo;

    @Autowired
    TimekeepingBusImpl timekeepingBus;

    @Override
    public DashboardModel getDashboardStatistical(DashboardFilter filter, UserInfo userInfo, String platform)
            throws BusinessException {
        DashboardModel dashboardModel = new DashboardModel();
        List<User> users = userRepo.getTotalPersonnel(filter.getCompanyId(), filter.getBranchId());
        Integer totalLateForWork = 0;
        for (User user : users) {
            TimekeepingHistoryModel timekeepingHistoryModel
                    = timekeepingBus.getTimekeepingByDay(filter.getDay(), user, platform);
            if (timekeepingHistoryModel != null && timekeepingHistoryModel.getLateWorkingHours() > 0) {
                totalLateForWork += 1;
            }
        }
        Integer totalCheckin = timekeepingRecordRepo.getTotalCheckin(filter);
        Integer totalNotCheckin = timekeepingRecordRepo.getTotalNotCheckin(filter);
        Integer totalSabbatical = sabbaticalRepo.getTotalSabbatical(filter);
        dashboardModel.setTotalPersonnel(users.size());
        dashboardModel.setTotalCheckin(totalCheckin);
        dashboardModel.setTotalLateForWork(totalLateForWork);
        dashboardModel.setTotalSabbatical(totalSabbatical);
        dashboardModel.setTotalNotCheckin(totalNotCheckin);
        return dashboardModel;
    }
}