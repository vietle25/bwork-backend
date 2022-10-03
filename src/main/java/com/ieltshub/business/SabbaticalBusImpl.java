package com.ieltshub.business;

import com.ieltshub.entity.Department;
import com.ieltshub.entity.Sabbatical;
import com.ieltshub.entity.User;
import com.ieltshub.entity.UserBranch;
import com.ieltshub.enumeration.ApprovalStatusType;
import com.ieltshub.enumeration.SabbaticalOffType;
import com.ieltshub.enumeration.StatusType;
import com.ieltshub.exception.BusinessException;
import com.ieltshub.repository.DepartmentRepository;
import com.ieltshub.repository.SabbaticalRepository;
import com.ieltshub.repository.UserBranchRepository;
import com.ieltshub.repository.WorkingTimeConfigRepository;
import com.ieltshub.utility.DateUtility;
import com.ieltshub.utility.StringUtility;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.sabbatical.RegisterSabbaticalFilter;
import com.ieltshub.viewmodel.sabbatical.SabbaticalFilter;
import com.ieltshub.viewmodel.sabbatical.SabbaticalModel;
import com.ieltshub.viewmodel.timekeeping.wokingTime.WorkingTimeConfigModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SabbaticalBusImpl extends AbstractBusiness implements SabbaticalBus {

    @Autowired
    TimekeepingBusImpl timekeepingBus;

    @Autowired
    SabbaticalRepository sabbaticalRepo;

    @Autowired
    DepartmentRepository departmentRepo;

    @Autowired
    WorkingTimeConfigRepository workingTimeConfigRepo;

    @Autowired
    UserBranchRepository userBranchRepo;

    @Override
    public PaginationResult<SabbaticalModel> getSabbaticals(Long userId, SabbaticalFilter filter) throws BusinessException {
        if(!filter.getDay().equals("All")) {
            String day = filter.getMonth() + "-" + filter.getDay();
            filter.setDay(day);
        } else {
            filter.setDay(null);
        }
        PaginationResult<Sabbatical> sabbaticalPaginationResult =
                sabbaticalRepo.getSabbaticals(userId, filter);
        PaginationResult<SabbaticalModel> result = new PaginationResult<>(filter.getPaging());
        result.setData(sabbaticalPaginationResult.getData().stream().map(item -> {
            SabbaticalModel model = new SabbaticalModel(item);
            return model;
        }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public PaginationResult<SabbaticalModel> getSabbaticalsAdmin(Long userId, SabbaticalFilter filter) throws BusinessException {
        if(!filter.getDay().equals("All")) {
            String day = filter.getMonth() + "-" + filter.getDay();
            filter.setDay(day);
        } else {
            filter.setDay(null);
        }
        PaginationResult<Sabbatical> sabbaticalAdminPaginationResult = sabbaticalRepo.getSabbaticalsAdmin(userId, filter);
        PaginationResult<SabbaticalModel> result = new PaginationResult<>(filter.getPaging());
        result.setData(sabbaticalAdminPaginationResult.getData().stream().map(item -> {
            SabbaticalModel model = new SabbaticalModel(item);
            return model;
        }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public Boolean hasSabbatical(Long userId, RegisterSabbaticalFilter filter) {
        Date offFromDateSql =
                DateUtility.convertStringToDate(
                        DateUtility.convertDateWithFormat(filter.getOffFromDate(),
                                DateUtility.FORMAT_DATE, DateUtility.FORMAT_DATE_SQL)
                );
        Date offToDateSql =
                DateUtility.convertStringToDate(
                        DateUtility.convertDateWithFormat(filter.getOffToDate() != null
                                        ? filter.getOffToDate() : filter.getOffFromDate(),
                                DateUtility.FORMAT_DATE, DateUtility.FORMAT_DATE_SQL)
                );
        return sabbaticalRepo.countSabbaticalExist(userId,
                StatusType.ACTIVE.getValue(), ApprovalStatusType.DENIED.getValue(),
                offFromDateSql, offToDateSql, filter.getOffType());
    }

    @Override
    public SabbaticalModel registerSabbatical(Long userId, RegisterSabbaticalFilter filter, String platform) throws BusinessException {
        initialize();
        Optional<User> userOptional = userRepo.findById(userId);
        Department department = departmentRepo.getDepartment(userId, StatusType.ACTIVE.getValue());
        UserBranch userBranch = userBranchRepo.getUserBranchByUserId(userId, StatusType.ACTIVE.getValue());
        WorkingTimeConfigModel workingTimeConfig = timekeepingBus.getWorkingTimeConfig(userId, platform);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Sabbatical sabbatical = new Sabbatical();
            setSabbatical(sabbatical, filter);
            sabbatical.setCompany(user.getCompany());
            sabbatical.setUser(user);
            sabbatical.setDepartment(department);
            sabbatical.setBranch(userBranch != null ? userBranch.getBranch() : null);
            sabbatical.setWorkingTimeConfig(workingTimeConfigRepo.findById(workingTimeConfig.getId()).get());
            sabbatical.setApprovalStatus(ApprovalStatusType.WAITING_FOR_APPROVAL);
            sabbatical.setStatus(StatusType.ACTIVE);
            sabbatical.setCreatedAt(sysTimestamp);
            sabbatical.setCreatedBy(user);
            sabbatical = sabbaticalRepo.save(sabbatical);
            return new SabbaticalModel(sabbatical);
        }
        return null;
    }

    @Override
    public SabbaticalModel updateSabbatical(Long userId, Long sabbaticalId,
                                            RegisterSabbaticalFilter filter, String platform)
            throws BusinessException {
        initialize();
        Optional<User> userOptional = userRepo.findById(userId);
        Optional<Sabbatical> sabbaticalOptional = sabbaticalRepo.findById(sabbaticalId);
        WorkingTimeConfigModel workingTimeConfig = timekeepingBus.getWorkingTimeConfig(userId, platform);
        if (userOptional.isPresent() && sabbaticalOptional.isPresent()) {
            User user = userOptional.get();
            Sabbatical sabbatical = sabbaticalOptional.get();
            setSabbatical(sabbatical, filter);
            sabbatical.setWorkingTimeConfig(workingTimeConfigRepo.findById(workingTimeConfig.getId()).get());
            sabbatical.setUpdatedAt(sysTimestamp);
            sabbatical.setUpdatedBy(user);
            sabbatical = sabbaticalRepo.save(sabbatical);
            return new SabbaticalModel(sabbatical);
        }
        return null;
    }

    private void setSabbatical(Sabbatical sabbatical, RegisterSabbaticalFilter filter) {
        sabbatical.setOffReason(filter.getOffReason());
        sabbatical.setOffType(SabbaticalOffType.parse(filter.getOffType()));
        sabbatical.setOffFromDate(DateUtility.convertStringToDate(
                DateUtility.convertDateWithFormat(filter.getOffFromDate(), DateUtility.FORMAT_DATE, DateUtility.FORMAT_DATE_SQL)
        ));
        if (filter.getOffToDate() != null && StringUtility.isNotEmpty(filter.getOffToDate())) {
            sabbatical.setOffToDate(DateUtility.convertStringToDate(
                    DateUtility.convertDateWithFormat(filter.getOffToDate(), DateUtility.FORMAT_DATE, DateUtility.FORMAT_DATE_SQL)
            ));
        }
    }

    @Override
    public Boolean deleteSabbatical(Long sabbaticalId, Long userId) throws BusinessException {
        initialize();
        Optional<Sabbatical> sabbaticalOptional = sabbaticalRepo.findById(sabbaticalId);
        if (sabbaticalOptional.isPresent()) {
            Sabbatical sabbatical = sabbaticalOptional.get();
            sabbatical.setStatus(StatusType.DELETE);
            sabbatical.setDeletedAt(sysTimestamp);
            sabbatical.setDeletedBy(userRepo.findById(userId).get());
            sabbaticalRepo.save(sabbatical);
            return true;
        }
        return false;
    }

    @Override
    public Boolean approvedSabbatical(Long sabbaticalId, Long userId) throws BusinessException {
        initialize();
        Optional<Sabbatical> sabbaticalOptional = sabbaticalRepo.findById(sabbaticalId);
        Optional<User> optionalUser = userRepo.findById(userId);
        if (sabbaticalOptional.isPresent()) {
            Sabbatical sabbatical = sabbaticalOptional.get();
            sabbatical.setApprovedAt(sysTimestamp);
            sabbatical.setApprovalStatus(ApprovalStatusType.APPROVED);
            if(optionalUser.isPresent()) {
                sabbatical.setApprovedBy(optionalUser.get());
            }
            sabbaticalRepo.save(sabbatical);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deniedSabbatical(Long sabbaticalId, Long userId, String deniedNote) throws BusinessException {
        initialize();
        Optional<Sabbatical> sabbaticalOptional = sabbaticalRepo.findById(sabbaticalId);
        Optional<User> optionalUser = userRepo.findById(userId);
        if (sabbaticalOptional.isPresent()) {
            Sabbatical sabbatical = sabbaticalOptional.get();
            sabbatical.setDeniedAt(sysTimestamp);
            sabbatical.setApprovalStatus(ApprovalStatusType.DENIED);
            sabbatical.setDeniedNote(deniedNote);
            if(optionalUser.isPresent()) {
                sabbatical.setDeniedBy(optionalUser.get());
            }
            sabbaticalRepo.save(sabbatical);
            return true;
        }
        return false;
    }

}
