
package com.evowork.business;

import com.evowork.entity.User;
import com.evowork.exception.BusinessException;
import com.evowork.repository.SabbaticalRepository;
import com.evowork.repository.TimekeepingRecordRepository;
import com.evowork.repository.UserRepository;
import com.evowork.viewmodel.cache.UserInfo;
import com.evowork.viewmodel.company.DashboardFilter;
import com.evowork.viewmodel.company.DashboardModel;
import com.evowork.viewmodel.timekeeping.history.TimekeepingHistoryModel;
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