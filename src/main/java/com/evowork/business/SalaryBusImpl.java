
package com.evowork.business;

import com.evowork.entity.*;
import com.evowork.enumeration.ApprovalStatusType;
import com.evowork.enumeration.SalaryType;
import com.evowork.enumeration.StatusType;
import com.evowork.exception.BusinessException;
import com.evowork.repository.*;
import com.evowork.utility.DateUtility;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.salary.SalaryDetailModel;
import com.evowork.viewmodel.salary.SalaryFilter;
import com.evowork.viewmodel.salary.SalaryModel;
import com.evowork.viewmodel.timekeeping.history.TimekeepingHistoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User bus
 *
 * @since 1.0
 */
@Service
public class SalaryBusImpl extends AbstractBusiness implements SalaryBus {

    @Autowired
    TimekeepingBusImpl timekeepingBus;

    @Autowired
    SalaryRepository salaryRepo;

    @Autowired
    SalaryDetailRepository salaryDetailRepo;

    @Autowired
    UserSalaryConfigRepository userSalaryConfigRepo;

    @Autowired
    WorkingTimeConfigRepository workingTimeConfigRepo;

    @Autowired
    SabbaticalRepository sabbaticalRepo;

    @Autowired
    TimekeepingRecordRepository timekeepingRecordRepo;

    @Override
    public SalaryModel getSalary(Long userId, SalaryFilter filter, String platform) throws BusinessException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            Salary salary = salaryRepo.getSalaryByUserId(userId, filter.getMonth());
            if (salary != null) {
                SalaryModel salaryModel = new SalaryModel(salary);
                List<SalaryDetail> salaryDetails = salaryDetailRepo.getSalaryDetailById(userId, salary.getId(),
                        StatusType.ACTIVE.getValue());
                if (salaryDetails != null) {
                    BigDecimal totalBonus = BigDecimal.valueOf(0);
                    BigDecimal totalFine = BigDecimal.valueOf(0);
                    BigDecimal totalTemporary = BigDecimal.valueOf(0);
                    BigDecimal totalWorkdays = BigDecimal.valueOf(0);
                    BigDecimal totalWorkingHours = BigDecimal.valueOf(0);
                    BigDecimal totalLackTime = BigDecimal.valueOf(0);
                    BigDecimal totalPlanWorkingHours = BigDecimal.valueOf(0);
                    for (SalaryDetail salaryDetail : salaryDetails) {
                        if (salaryDetail.getType().getValue() == SalaryType.BONUS.getValue()) {
                            if (salaryDetail.getAmount() != null) {
                                totalBonus = totalBonus.add(salaryDetail.getAmount());
                            }
                        } else if (salaryDetail.getType().getValue() == SalaryType.FINE.getValue()) {
                            if (salaryDetail.getAmount() != null) {
                                totalFine = totalFine.add(salaryDetail.getAmount());
                            }
                        } else if (salaryDetail.getType().getValue() == SalaryType.TEMPORARY_SALARY.getValue()) {
                            if (salaryDetail.getAmount() != null) {
                                totalTemporary = totalTemporary.add(salaryDetail.getAmount());
                            }
                        } else if (salaryDetail.getType().getValue() == SalaryType.WORKDAYS.getValue()) {
                            if (salaryDetail.getAmount() != null) {
                                totalWorkdays = totalWorkdays.add(salaryDetail.getAmount());
                            }
                        } else if (salaryDetail.getType().getValue() == SalaryType.WORKING_HOURS.getValue()) {
                            if (salaryDetail.getAmount() != null) {
                                totalWorkingHours = totalWorkingHours.add(salaryDetail.getAmount());
                            }
                        } else if (salaryDetail.getType().getValue() == SalaryType.LACK_TIME.getValue()) {
                            if (salaryDetail.getAmount() != null) {
                                totalLackTime = totalLackTime.add(salaryDetail.getAmount());
                            }
                        } else if (salaryDetail.getType().getValue() == SalaryType.TOTAL_PLAN_WORKING_HOURS.getValue()) {
                            if (salaryDetail.getAmount() != null) {
                                totalPlanWorkingHours = totalPlanWorkingHours.add(salaryDetail.getAmount());
                            }
                        }
                    }
                    salaryModel.setTotalWorkdays(totalWorkdays);
                    salaryModel.setTotalBonusAmount(totalBonus);
                    salaryModel.setTotalFineAmount(totalFine);
                    salaryModel.setTotalTemporaryAmount(totalTemporary);
                    salaryModel.setTotalWorkingHours(totalWorkingHours);
                    salaryModel.setTotalLackTime(totalLackTime);
                    salaryModel.setTotalPlanWorkingHours(totalPlanWorkingHours);
                    UserSalaryConfig userSalaryConfig = userSalaryConfigRepo.getUserSalaryConfigByUserId(
                            userId, filter.getFirstDayOfMonth()
                    );
                    if (userSalaryConfig != null) {
                        salaryModel.setSalary(userSalaryConfig.getAmount());
                        if (userSalaryConfig.getInputType() != null) {
                            salaryModel.setInputType(userSalaryConfig.getInputType().getValue());
                        }
                    }
                    salaryModel.setNetAmount(salary.getNetAmount());
                    WorkingTimeConfig workingTimeConfig = workingTimeConfigRepo
                            .getWorkingTimeConfigByMonth(
                                    userId,
                                    DateUtility.convertDateWithFormat(
                                            filter.getFirstDayOfMonth(),
                                            DateUtility.FORMAT_DATE_SQL,
                                            DateUtility.FORMAT_MONTH_OF_YEAR),
                                    userRepo.findById(userId).get().getCompany().getId()
                            );
                    if (workingTimeConfig != null) {
                        salaryModel.setNumDayOffInMonth(workingTimeConfig.getNumDayOffInMonth());
                    }

                    String month = DateUtility.convertDateWithFormat(filter.getMonth(), "MM-yyyy", "yyyy-MM");
                    Integer numDaySabbatical = sabbaticalRepo.countSabbaticalApprovedByMonth(
                            userId, StatusType.ACTIVE.getValue(), ApprovalStatusType.APPROVED.getValue(), month
                    );
                    salaryModel.setNumDaySabbatical(numDaySabbatical);
                    Integer numLateForWork = 0;
                    List<String> dayOfMonths = timekeepingRecordRepo.getDayOfMonth(userId, month);
                    if (dayOfMonths != null && dayOfMonths.size() > 0) {
                        for (String day : dayOfMonths) {
                            TimekeepingHistoryModel timekeepingHistoryModel
                                    = timekeepingBus.getTimekeepingByDay(day, userOptional.get(), platform);
                            if (timekeepingHistoryModel != null && timekeepingHistoryModel.getLateWorkingHours() > 0) {
                                numLateForWork += 1;
                            }
                        }
                    }
                    salaryModel.setNumLateForWork(numLateForWork);
                    return salaryModel;
                }
                return null;
            }
        }
        return null;
    }

    @Override
    public SalaryModel getSalaryConfig(SalaryFilter filter) throws BusinessException {
        SalaryModel salaryModel = null;
        UserSalaryConfig userSalaryConfig = userSalaryConfigRepo.getUserSalaryConfigByUserId(
                filter.getUserId(), filter.getFirstDayOfMonth()
        );
        if (userSalaryConfig != null) {
            salaryModel = new SalaryModel();
            salaryModel.setSalary(userSalaryConfig.getAmount());
            if (userSalaryConfig.getInputType() != null) {
                salaryModel.setInputType(userSalaryConfig.getInputType().getValue());
            }
        }
        return salaryModel;
    }

    @Override
    public PaginationResult<SalaryDetailModel> getDetailTypeSalary(Long userId, SalaryFilter filter) throws BusinessException {
        PaginationResult<SalaryDetail> salaryDetail =
                salaryDetailRepo.getTypeAmount(userId, filter.getSalaryId(), filter.getType(), filter.getPaging());
        PaginationResult<SalaryDetailModel> result = new PaginationResult<>(salaryDetail.getPaging());
        result.setData(salaryDetail.getData().stream().map(item -> {
            SalaryDetailModel model = new SalaryDetailModel(item);
            return model;
        }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public PaginationResult<SalaryDetailModel> getStaffSalary(Long userId, SalaryFilter filter) throws BusinessException {
        PaginationResult<SalaryDetail> salary = salaryDetailRepo.getStaffSalary(userId, filter);
        PaginationResult<SalaryDetailModel> result = new PaginationResult<>(filter.getPaging());
        result.setData(salary.getData().stream().map(item -> {
            SalaryDetailModel model = new SalaryDetailModel(item);
            return model;
        }).collect(Collectors.toList()));
        return result;
    }

}