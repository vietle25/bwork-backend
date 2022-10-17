
package com.evowork.business;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.*;
import com.evowork.common.ErrorCode;
import com.evowork.config.AppConstant;
import com.evowork.entity.*;
import com.evowork.enumeration.*;
import com.evowork.exception.BusinessException;
import com.evowork.model.AllConversation;
import com.evowork.model.LastMessage;
import com.evowork.model.Members;
import com.evowork.model.MessageByConversation;
import com.evowork.repository.*;
import com.evowork.utility.DateUtility;
import com.evowork.utility.EmailHelper;
import com.evowork.utility.StringUtility;
import com.evowork.viewmodel.cache.UserInfo;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.common.Paging;
import com.evowork.viewmodel.company.CompanyFilter;
import com.evowork.viewmodel.company.CompanyModel;
import com.evowork.viewmodel.conversation.ConversationFilter;
import com.evowork.viewmodel.conversation.ConversationMemberModel;
import com.evowork.viewmodel.conversation.ConversationModel;
import com.evowork.viewmodel.department.DepartmentFilter;
import com.evowork.viewmodel.department.DepartmentModel;
import com.evowork.viewmodel.location.BranchModel;
import com.evowork.viewmodel.timekeeping.wokingTime.WorkingTimeConfigModel;
import com.evowork.viewmodel.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User bus
 *
 * @since 1.0
 */
@Service
public class UserBusImpl extends AbstractBusiness implements UserBus {

    @Value("classpath:static/reset_password_template.txt")
    private Resource resetPasswordTemplateResource;

    @Autowired
    CommonBusImpl commonBus;

    @Autowired
    UserRepository userRepo;

    @Autowired
    NotificationRepository notificationRepo;

    @Autowired
    AppConfigRepository appConfigRepo;

    @Autowired
    UserNotificationRepository userNotificationRepo;

    @Autowired
    UserDeviceRepository userDeviceRepo;

    @Autowired
    UserResourceRepository userResourceRepo;

    @Autowired
    CategoryRepository categoryRepo;

    @Autowired
    ConversationMemberRepository conversationMemberRepo;

    @Autowired
    ConversationRepository conversationRepo;

    @Autowired
    UserLoginLogRepository userLoginLogRepo;

    @Autowired
    CompanyRepository companyRepo;

    @Autowired
    DepartmentRepository departmentRepo;

    @Autowired
    DepartmentStaffRepository departmentStaffRepo;

    @Autowired
    BranchRepository branchRepo;

    @Autowired
    UserBranchRepository userBranchRepo;

    @Autowired
    WorkingTimeConfigRepository workingTimeConfigRepo;

    @Autowired
    UserSalaryConfigRepository userSalaryConfigRepo;

    @Autowired
    EmailHelper emailHelper;

    @Autowired
    TimekeepingBusImpl timekeepingBus;

    @Override
    public User getUserByEmail(String email, Integer userType) {
        return userRepo.getUserByEmail(email, StatusType.DELETE.getValue(), userType);
    }

    @Override
    public User getUserByPhone(String phone, Integer userType) {
        return userRepo.getUserByPhone(phone, StatusType.DELETE.getValue(), userType);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.getUserByEmail(email, StatusType.DELETE.getValue());
    }

    @Override
    public User getUserByPhone(String phone) {
        return userRepo.getUserByPhone(phone, StatusType.DELETE.getValue());
    }

    @Override
    public Boolean deleteUserExperience(Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(StatusType.DELETE);
            userRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Company getCompany(String code) {
        return companyRepo.getCompanyByCode(code, StatusType.ACTIVE.getValue());
    }

    @Override
    public String getCompanyCodeByUserId(Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getCompany() != null) {
                return user.getCompany().getCode();
            }
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        return userRepo.save(user);
    }

    private User getUserByUserId(Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    @Override
    public UserDTO getProfile(Long userId, String deviceId, String bundleId) throws BusinessException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO(user);
            userDTO.setUserResources(this.getUserResource(userId));
            if (bundleId.equals(AppConstant.APP_USER)) {
                if (user.getPhone().equals(AppConstant.PHONE_GOVERNOR)
                        || user.getCompany().getCode().equals(AppConstant.COMPANY_CODE_DEFAULT)) {
                    userDTO.setUserLoginLog(new UserLoginLogModel());
                } else {
                    userDTO.setUserLoginLog(this.getUserDeviceLogin(userId, deviceId));
                }
                userDTO.setBranch(this.getUserBranch(userId, user.getCompany().getId()));
            } else {
                userDTO.setBranch(this.getBranch(userId, user.getCompany().getId(), bundleId));
            }
            userDTO.setCompany(this.getCompanyDetail(user.getCompany().getId()));
            userDTO.setDepartment(this.getDepartment(userId));
            if (user.getStaffType() != null) {
                userDTO.setStaff(this.getStaff(user.getStaffType().getId()));
            }
            return userDTO;
        }
        return null;
    }

    public DepartmentModel getDepartment(Long userId) {
        Department department = departmentRepo.getDepartment(userId, StatusType.ACTIVE.getValue());
        DepartmentModel departmentModel = null;
        if (department != null) {
            departmentModel = new DepartmentModel(department);
        }
        return departmentModel;
    }

    public BranchModel getUserBranch(Long userId, Long companyId) {
        Branch branch = branchRepo.getBranch(companyId, userId, StatusType.ACTIVE.getValue());
        BranchModel branchModel = null;
        if (branch != null) {
            branchModel = new BranchModel(branch);
        }
        return branchModel;
    }

    public CategoryModel getStaff(Long staffTypeId) {
        if (staffTypeId != null) {
            Optional<Category> categoryOptional = categoryRepo.findById(staffTypeId);
            CategoryModel categoryModel = null;
            if (categoryOptional.isPresent()) {
                categoryModel = new CategoryModel(categoryOptional.get());
            }
            return categoryModel;
        }
        return null;
    }

    @Override
    public Boolean updateDepartment(DepartmentFilter filter) throws BusinessException {
        initialize();
        Optional<User> userOptional = userRepo.findById(filter.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Company company = user.getCompany();
            if (filter.getBranchId() != null) {
                Optional<Branch> branchOptional = branchRepo.findById(filter.getBranchId());
                if (branchOptional.isPresent()) {
                    Branch branch = branchOptional.get();
                    UserBranch userBranch = userBranchRepo.getUserBranchByUserId(
                            user.getId(), StatusType.ACTIVE.getValue());
                    if (userBranch == null) {
                        userBranch = new UserBranch();
                    }
                    userBranch.setUser(user);
                    userBranch.setBranch(branch);
                    userBranch.setStatus(StatusType.ACTIVE);
                    userBranch.setCreatedAt(sysTimestamp);
                    userBranch.setCreatedBy(user);
                    userBranch.setCompany(company);
                    userBranchRepo.save(userBranch);
                }
            }
            if (filter.getDepartmentId() != null) {
                Optional<Department> departmentOptional = departmentRepo.findById(filter.getDepartmentId());
                if (departmentOptional.isPresent()) {
                    Department department = departmentOptional.get();
                    DepartmentStaff departmentStaff = new DepartmentStaff();
                    departmentStaff.setCompany(company);
                    departmentStaff.setUser(user);
                    departmentStaff.setDepartment(department);
                    departmentStaff.setStatus(StatusType.ACTIVE);
                    departmentStaff.setCreatedAt(sysTimestamp);
                    departmentStaff.setCreatedBy(user);
                    departmentStaffRepo.save(departmentStaff);
                }
            }
            if (filter.getStaffId() != null) {
                Optional<Category> categoryOptional = categoryRepo.findById(filter.getStaffId());
                if (categoryOptional.isPresent()) {
                    user.setStaffType(categoryOptional.get());
                    userRepo.save(user);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PaginationResult<CompanyModel> getCompanies(CompanyFilter filter, Long userId) throws BusinessException {
        PaginationResult<Company> companyPaginationResult = companyRepo.getCompanies(userId, filter);
        PaginationResult<CompanyModel> result = new PaginationResult<>(companyPaginationResult.getPaging());
        result.setData(companyPaginationResult.getData().stream().map(item -> {
            CompanyModel model = new CompanyModel(item);
            // Get list branches
            List<Branch> branches = branchRepo.getBranches(item.getId(), StatusType.ACTIVE.getValue());
            List<BranchModel> branchModels = branches.stream().map(branch -> {
                return new BranchModel(branch);
            }).collect(Collectors.toList());
            model.setBranches(branchModels);

            // Get path resource
            return model;
        }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public PaginationResult<UserDTO> getListUserChat(UserFilter filter, Long userId) throws BusinessException {
        PaginationResult<User> user = userRepo.getListUserChat(filter, userId);
        PaginationResult<UserDTO> result = new PaginationResult<>(user.getPaging());
        result.setData(user.getData().stream().map(item -> {
            UserDTO model = new UserDTO(item);
            if (filter.getUserType() != null && filter.getUserType() == UserType.WEB_ADMIN.getValue()) {
                Optional<User> userChat = userRepo.findById(item.getId());
                if (userChat.isPresent()) {
                    model.setConversationId(conversationMemberRepo.checkExistConversation(
                            userChat.get().getId(),
                            userId));
                }
            }
            return model;
        }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public PaginationResult<BranchModel> getBranches(CompanyFilter filter, Long userId, Long companyId) throws BusinessException {
        PaginationResult<Branch> branchPaginationResult = branchRepo.getBranches(userId, companyId, filter);
        PaginationResult<BranchModel> result = new PaginationResult<>(branchPaginationResult.getPaging());
        result.setData(branchPaginationResult.getData().stream().map(item -> {
            BranchModel model = new BranchModel(item);
            return model;
        }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public Boolean configStaff(Long userId, Long staffId, ConfigStaffFilter filter) throws BusinessException {
        initialize();
        Optional<User> staffOptional = userRepo.findById(staffId);
        Optional<User> adminOptional = userRepo.findById(userId);
        String month = DateUtility.convertDateWithFormat(filter.getValidFrom(),
                DateUtility.FORMAT_DATE_SQL, DateUtility.FORMAT_MONTH_OF_YEAR);
        if (staffOptional.isPresent() && adminOptional.isPresent()) {
            User staff = staffOptional.get();
            User admin = adminOptional.get();
            UserSalaryConfig userSalaryConfig = userSalaryConfigRepo.getUserSalaryConfigExistByMonth(
                    staffId, month
            );
            if (userSalaryConfig != null) {
            } else {
                userSalaryConfig = new UserSalaryConfig();
                userSalaryConfig.setCreatedAt(sysTimestamp);
                userSalaryConfig.setCreatedBy(admin);
            }
            userSalaryConfig.setUser(staff);
            userSalaryConfig.setAmount(filter.getSalaryNumber());
            userSalaryConfig.setInputType(UserSalaryInputType.parse(filter.getSalaryInputType()));
            userSalaryConfig.setValid(month + "-01");
            userSalaryConfigRepo.save(userSalaryConfig);
            if (filter.getStartWorkingTime1() != null) {
                WorkingTimeConfig workingTimeConfig = workingTimeConfigRepo.getWorkingTimeConfigExistByDay(
                        staffId, filter.getValidFrom(), staff.getCompany().getId()
                );
                if (workingTimeConfig != null) {
                    workingTimeConfig.setUpdatedAt(sysTimestamp);
                    workingTimeConfig.setUpdatedBy(admin);
                } else {
                    workingTimeConfig = new WorkingTimeConfig();
                    UserBranch userBranch = userBranchRepo.getUserBranchByUserId(staffId, StatusType.ACTIVE.getValue());
                    workingTimeConfig.setWorkingTimeType(WorkingTimeType.SPECIFIC_WORKING_TIME_FOR_USER);
                    workingTimeConfig.setStatus(StatusType.ACTIVE);
                    workingTimeConfig.setUser(staff);
                    workingTimeConfig.setCreatedAt(sysTimestamp);
                    workingTimeConfig.setCreatedBy(admin);
                    workingTimeConfig.setCompany(staff.getCompany());
                    if (userBranch != null) {
                        workingTimeConfig.setBranch(userBranch.getBranch());
                    }
                }
                workingTimeConfig.setNumWorkingHours(filter.getNumWorkingHour());
                workingTimeConfig.setNumDayOffInMonth(filter.getNumDayOffInMonth());
                workingTimeConfig.setStartWorkingTime1(filter.getStartWorkingTime1());
                workingTimeConfig.setEndWorkingTime1(filter.getEndWorkingTime1());
                workingTimeConfig.setStartWorkingTime2(filter.getStartWorkingTime2());
                workingTimeConfig.setEndWorkingTime2(filter.getEndWorkingTime2());
                workingTimeConfig.setUpdatedAt(sysTimestamp);
                workingTimeConfig.setUpdatedBy(admin);
                workingTimeConfig.setValidFrom(filter.getValidFrom());
                workingTimeConfig.setShiftType(WorkingTimeShiftType.parse(filter.getWorkingTimeShiftType()));
                workingTimeConfigRepo.save(workingTimeConfig);
            }
            staff.setStatus(StatusType.ACTIVE);
            staff.setStartWorkTime(sysTimestamp);
            staff.setUpdatedAt(sysTimestamp);
            staff.setUpdatedBy(admin);
            userRepo.save(staff);
            return true;
        }
        return false;
    }

    private List<UserResourceModel> getUserResource(Long userId) {
        List<UserResourceModel> userResourceModels = new ArrayList<>();
        List<UserResource> userResources = userResourceRepo
                .getUserResource(userId, UserResourceType.PERSONAL_FILE.getValue());
        if (userResources != null) {
            for (UserResource userResource : userResources) {
                UserResourceModel userResourceModel = new UserResourceModel(userResource);
                userResourceModels.add(userResourceModel);
            }
        }
        return userResourceModels;
    }

    private UserLoginLogModel getUserDeviceLogin(Long userId, String deviceId) {
        UserLoginLog userDeviceLogin = userLoginLogRepo
                .getUserDeviceLogin(deviceId, userId, StatusType.ACTIVE.getValue());
        UserLoginLogModel userLoginLogModel = null;
        if (userDeviceLogin != null) {
            userLoginLogModel = new UserLoginLogModel(userDeviceLogin);
        }
        return userLoginLogModel;
    }

    @Override
    public ErrorCode checkUserLoginLog(User user, DeviceInfo deviceInfo, String platform) {
        List<UserLoginLog> deviceLoginLogs = userLoginLogRepo
                .getDeviceLoginLog(deviceInfo.getDeviceId(), StatusType.ACTIVE.getValue());
        if (deviceLoginLogs != null && deviceLoginLogs.size() > 0) {
            // Thiet bi da co user dang nhap
            UserLoginLog deviceLoginLog = null;
            UserLoginLog ownerDeviceLoginLog = null;
            UserLoginLog ownerUserLoginLog = null;
            for (UserLoginLog deviceLogin : deviceLoginLogs) {
                if (deviceLogin.getUser().getId() == user.getId()) {
                    deviceLoginLog = deviceLogin;
                    break;
                }
                if (deviceLogin.getOwner()) {
                    ownerDeviceLoginLog = deviceLogin;
                }
            }
            if (deviceLoginLog == null) {
                List<UserLoginLog> userLoginLogs = userLoginLogRepo
                        .getUserLoginLog(user.getId(), StatusType.ACTIVE.getValue());
                if (userLoginLogs != null && userLoginLogs.size() > 0) {
                    for (UserLoginLog userLogin : userLoginLogs) {
                        if (userLogin.getOwner()) {
                            ownerUserLoginLog = userLogin;
                        }
                    }
                    if (ownerDeviceLoginLog != null) {
                        // User chua tung dang nhap
                        return ErrorCode.DEVICE_HAS_ALREADY_LOGGED_IN;
                    } else {
                        if (ownerUserLoginLog == null) {
                            // User chua tung dang nhap thiet bi nao
                            this.insertNewUserLoginLog(user, deviceInfo, platform);
                            return ErrorCode.NO_ERROR;
                        } else {
                            // Da dang nhap o thiet bi khac
                            return ErrorCode.USER_LOGGED_IN_ANOTHER_DEVICE;
                        }
                    }
                } else {
                    if (ownerDeviceLoginLog != null) {
                        // User chua tung dang nhap
                        return ErrorCode.DEVICE_HAS_ALREADY_LOGGED_IN;
                    } else {
                        // User chua tung dang nhap thiet bi nao
                        this.insertNewUserLoginLog(user, deviceInfo, platform);
                        return ErrorCode.NO_ERROR;
                    }
                }
            }
            // Dang nhap vao thiet bi
            return ErrorCode.NO_ERROR;
        } else {
            // Thiet bi chua co user dang nhap
            List<UserLoginLog> userLoginLogs = userLoginLogRepo
                    .getUserLoginLog(user.getId(), StatusType.ACTIVE.getValue());
            if (userLoginLogs != null && userLoginLogs.size() > 0) {
                for (UserLoginLog userLogin : userLoginLogs) {
                    if (userLogin.getOwner()) {
                        return ErrorCode.USER_LOGGED_IN_ANOTHER_DEVICE;
                    }
                }
            }
            // User chua tung dang nhap thiet bi nao
            this.insertNewUserLoginLog(user, deviceInfo, platform);
            return ErrorCode.NO_ERROR;
        }
    }

    /**
     * Insert new user login log
     *
     * @param user
     * @param deviceInfo
     * @param platform
     */
    private UserLoginLog insertNewUserLoginLog(User user, DeviceInfo deviceInfo, String platform) {
        initialize();
        UserLoginLog loginLogOfUser = new UserLoginLog();
        loginLogOfUser.setUser(user);
        loginLogOfUser.setDeviceId(deviceInfo.getDeviceId());
        if (StringUtility.isNotEmpty(platform) && platform.equals("android")) {
            loginLogOfUser.setOsType(PlatformType.ANDROID);
        } else {
            loginLogOfUser.setOsType(PlatformType.IOS);
        }
        loginLogOfUser.setOsVersion(deviceInfo.getOsVersion());
        loginLogOfUser.setModel(deviceInfo.getModel());
        loginLogOfUser.setImei1(deviceInfo.getImei1());
        loginLogOfUser.setImei2(deviceInfo.getImei2());
        loginLogOfUser.setCreatedAt(sysTimestamp);
        loginLogOfUser.setOwner(true);
        userLoginLogRepo.save(loginLogOfUser);
        return loginLogOfUser;
    }

    @Override
    public List<NotificationModel> searchNotification(Long userId, Integer type, String stringSearch) {
        List<NotificationModel> models = new ArrayList<>();
        Optional<User> userOptional = userRepo.findById(userId);
        List<Notification> notifications;
        if (userOptional != null) {
            if (type != null && type > 0) {
                notifications = userNotificationRepo.searchNotificationByType(
                        userId,
                        type,
                        StringUtility.convertUppercaseToLowercase(StringUtility.toFullSearchLike(StringUtility.removeAccent(stringSearch)))
                );
            } else {
                notifications = userNotificationRepo.searchNotification(
                        userId,
                        StringUtility.convertUppercaseToLowercase(StringUtility.toFullSearchLike(StringUtility.removeAccent(stringSearch)))
                );
            }
            if (notifications.size() > 0) {
                for (int i = 0, size = notifications.size(); i < size; i++) {
                    models.add(new NotificationModel(notifications.get(i)));
                }
            }
        }
        return models;
    }

    @Override
    public Boolean viewAllNotification(UserInfo userInfo) throws BusinessException {
        Optional<User> userOptional = userRepo.findById(userInfo.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Notification> notifications =
                    notificationRepo.getAllNotificationNotSeenById(user.getId());
            if (notifications.size() > 0) {
                notifications.forEach(notification -> {
                    notification.setIsSeen(true);
                    notificationRepo.save(notification);
                });
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ErrorCode editProfile(CreateUserModel userModel, UserInfo userInfo) throws BusinessException {
        LoggingInfo loggingInfo = initialize(userInfo);
        User user = loggingInfo.getUser();
        if (user == null) {
            return ErrorCode.AUTHENTICATE_REQUIRED;
        }
        if (StringUtility.isNotEmpty(userModel.getPhone())) {
            if (userModel.getId() != null) {
                Optional<User> userOptional = userRepo.findById(userModel.getId());
                if (userOptional.isPresent()) {
                    user = userOptional.get();
                }
            }
            Boolean changeLoginPhone = !userModel.getPhone().trim().equals(user.getPhone());
            if (changeLoginPhone) {
                if (isNewUserPhoneExist(userModel.getPhone())) {
                    return ErrorCode.USER_EXIST_TRY_LOGIN_FAIL;
                }
                user.setPhone(userModel.getPhone());
            }
        }
        String fullName = userModel.getName().trim();
        user.setFirstName(this.handleName(fullName).getFirstName());
        user.setLastName(this.handleName(fullName).getLastName());
        user.setName(fullName);
        user.setBirthDate(userModel.getBirthDate());
        user.setAddress(userModel.getAddress());
        user.setGender(GenderType.parse(userModel.getGender()));
        user.setPersonalId(userModel.getPersonalId());
        user.setDomicile(userModel.getDomicile());
        user.setEmail(userModel.getEmail());
        user.setPersonId(userModel.getPersonId());
        user.setAvatarPath(userModel.getAvatarPath());
        userRepo.save(user);
        return ErrorCode.NO_ERROR;
    }

    /**
     * Check phone exist
     *
     * @param phone
     * @return
     */
    private boolean isNewUserPhoneExist(String phone) {
        return userRepo.isUserPhoneExist(phone);
    }

    @Override
    public PaginationResult<NotificationModel> getNotifications(Long userId, Integer type, Paging paging) throws BusinessException {
        PaginationResult<Notification> notifications
                = notificationRepo.getNotifications(userId, type, paging);
        PaginationResult<NotificationModel> result = new PaginationResult<>(notifications.getPaging());
        result.setData(notifications.getData().stream().map(item -> {
            NotificationModel model = new NotificationModel(item);
            return model;
        }).collect(Collectors.toList()));
        return result;
    }

    @Transactional
    @Override
    public Integer countNewNotification(Long userId) throws BusinessException {
        User user = getUserByUserId(userId);
        Integer numberNewNotification =
                notificationRepo.countNewNotification(userId);
        return numberNewNotification;
    }

    @Override
    public List<AppConfigModel> getMobileConfig(AppConfigFilter filter) throws BusinessException {
//        List<AppConfig> configs = appConfigRepo.getMobileConfig(filter);
//        List<AppConfigModel> model = configs.stream().map(item -> {
//            return new AppConfigModel(item);
//        }).collect(Collectors.toList());
        List<AppConfigModel> model = new ArrayList<>();
        AppConfigModel urlPath = getConfigHByName(filter, AppConstant.RES_URL_PATH);
        AppConfigModel urlPatResize = getConfigHByName(filter, AppConstant.RES_URL_PATH_RESIZE);
        AppConfigModel adminId = getConfigHByName(filter, AppConstant.USER_ADMIN_ID);
        AppConfigModel maxFileSize = getConfigHByName(filter, AppConstant.MAX_FILE_SIZE_UPLOAD);

        AppConfigModel checkInRule = getConfigHByName(filter, AppConstant.CHECK_IN_RULE);

        AppConfigModel minuteBeforeAbleCheckIn1 = getConfigHByName(filter, AppConstant.TIME_BEFORE_ABLE_TO_CHECK_IN_1);
        AppConfigModel minuteBeforeAbleCheckIn2 = getConfigHByName(filter, AppConstant.TIME_BEFORE_ABLE_TO_CHECK_IN_2);
        AppConfigModel minuteAfterCheckOut1 = getConfigHByName(filter, AppConstant.TIME_AFTER_ABLE_TO_CHECK_OUT_1);
        AppConfigModel minuteAfterCheckOut2 = getConfigHByName(filter, AppConstant.TIME_AFTER_ABLE_TO_CHECK_OUT_2);
        AppConfigModel minuteAfterCheckIn1 = getConfigHByName(filter, AppConstant.TIME_AFTER_ABLE_TO_CHECK_IN_1);
        AppConfigModel minuteAfterCheckIn2 = getConfigHByName(filter, AppConstant.TIME_AFTER_ABLE_TO_CHECK_IN_2);
        AppConfigModel minuteBeforeAbleCheckOut1 = getConfigHByName(filter, AppConstant.TIME_BEFORE_ABLE_TO_CHECK_OUT_1);
        AppConfigModel minuteBeforeAbleCheckOut2 = getConfigHByName(filter, AppConstant.TIME_BEFORE_ABLE_TO_CHECK_OUT_2);

        AppConfigModel minuteBeforeRemindCheckIn1 = getConfigHByName(filter, AppConstant.TIME_BEFORE_TO_REMIND_CHECK_IN_1);
        AppConfigModel minuteBeforeRemindCheckIn2 = getConfigHByName(filter, AppConstant.TIME_BEFORE_TO_REMIND_CHECK_IN_2);
        AppConfigModel minuteBeforeRemindCheckOut1 = getConfigHByName(filter, AppConstant.TIME_BEFORE_TO_REMIND_CHECK_OUT_1);
        AppConfigModel minuteBeforeRemindCheckOut2 = getConfigHByName(filter, AppConstant.TIME_BEFORE_TO_REMIND_CHECK_OUT_2);

        AppConfigModel workingPolicy = getConfigHByName(filter, AppConstant.WORKING_POLICY);
        AppConfigModel workingPolicyPrivate = getConfigHByName(filter, AppConstant.WORKING_POLICY_PRIVATE);
        AppConfigModel workingPolicyHoliday = getConfigHByName(filter, AppConstant.WORKING_POLICY_HOLIDAY_LEAVE);
        AppConfigModel workingPolicyTime = getConfigHByName(filter, AppConstant.WORKING_POLICY_TIME);
        if (urlPath != null) model.add(urlPath);
        if (urlPatResize != null) model.add(urlPatResize);
        if (adminId != null) model.add(adminId);
        if (maxFileSize != null) model.add(maxFileSize);
        if (checkInRule != null) model.add(checkInRule);
        if (minuteBeforeAbleCheckIn1 != null) model.add(minuteBeforeAbleCheckIn1);
        if (minuteBeforeAbleCheckIn2 != null) model.add(minuteBeforeAbleCheckIn2);
        if (minuteAfterCheckOut1 != null) model.add(minuteAfterCheckOut1);
        if (minuteAfterCheckOut2 != null) model.add(minuteAfterCheckOut2);
        if (minuteAfterCheckIn1 != null) model.add(minuteAfterCheckIn1);
        if (minuteAfterCheckIn2 != null) model.add(minuteAfterCheckIn2);
        if (minuteBeforeAbleCheckOut1 != null) model.add(minuteBeforeAbleCheckOut1);
        if (minuteBeforeAbleCheckOut2 != null) model.add(minuteBeforeAbleCheckOut2);
        if (minuteBeforeRemindCheckIn1 != null) model.add(minuteBeforeRemindCheckIn1);
        if (minuteBeforeRemindCheckIn2 != null) model.add(minuteBeforeRemindCheckIn2);
        if (minuteBeforeRemindCheckOut1 != null) model.add(minuteBeforeRemindCheckOut1);
        if (minuteBeforeRemindCheckOut2 != null) model.add(minuteBeforeRemindCheckOut2);
        if (workingPolicy != null) model.add(workingPolicy);
        if (workingPolicyPrivate != null) model.add(workingPolicyPrivate);
        if (workingPolicyHoliday != null) model.add(workingPolicyHoliday);
        if (workingPolicyTime != null) model.add(workingPolicyTime);
        return model;
    }

    private AppConfigModel getConfigHByName(AppConfigFilter filter, String configName) {
        AppConfig configHoliday;
        AppConfigModel appConfigModel = null;
        if (filter.getBranchId() != null) {
            configHoliday = appConfigRepo.getConfigByName(configName,
                    filter.getCompanyId(), filter.getBranchId());
            if (configHoliday != null) {
                return new AppConfigModel(configHoliday);
            }
        }
        configHoliday = appConfigRepo.getConfigByName(configName,
                filter.getCompanyId());
        if (configHoliday != null) {
            appConfigModel = new AppConfigModel(configHoliday);
        }
        return appConfigModel;
    }

    @Override
    public Boolean forgetPassword(CreateUserModel userModel) throws BusinessException {
        User user = userRepo.getUserByEmail(
                userModel.getEmail(),
                StatusType.DELETE.getValue(),
                UserType.NORMAL_USERS.getValue());
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public User getUser(Long userId) throws BusinessException {
        if (userId == null) {
            throw new IllegalArgumentException("user_id must not be null.");
        }
        Optional<User> user = userRepo.findById(userId);
        return user != null ? user.get() : null;
    }

    @Override
    public Boolean requestChangePass(Long userId, String oldPass, String newPass) throws BusinessException {
        User user = getUser(userId);
        if (user != null) {
            user.setPassword(newPass);
            userRepo.save(user);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserDTO signUp(CreateUserModel userModel, Company company, String platform) throws BusinessException {
        initialize();
        User user = new User();
        String fullName = userModel.getName().trim();
        user.setName(fullName);
        user.setFirstName(this.handleName(fullName).getFirstName());
        user.setLastName(this.handleName(fullName).getLastName());
        if (StringUtility.isNotEmpty(userModel.getPhone())) {
            user.setPhone(userModel.getPhone());
        }
        user.setEmail(userModel.getEmail());
        user.setBirthDate(sysTimestamp);
        user.setPassword(userModel.getPassword());
        user.setUserType(UserType.NORMAL_USERS);
        user.setGender(GenderType.OTHER);
        if (company.getCode().equals(AppConstant.COMPANY_CODE_DEFAULT)) {
            user.setStatus(StatusType.ACTIVE);
            user.setStartWorkTime(sysTimestamp);
        } else {
            user.setStatus(StatusType.DRAFT);
        }
        user.setCreatedAt(sysTimestamp);
        user.setCompany(company);
        user = userRepo.save(user);
        if (company.getCode().equals(AppConstant.COMPANY_CODE_DEFAULT)) {
            WorkingTimeConfigModel workingTimeConfig = timekeepingBus.getWorkingTimeConfig(user.getId(), platform);
            ConfigStaffFilter configStaffFilter = new ConfigStaffFilter();
            if (workingTimeConfig != null) {
                configStaffFilter.setStartWorkingTime1(workingTimeConfig.getStartWorkingTime1());
                configStaffFilter.setEndWorkingTime1(workingTimeConfig.getEndWorkingTime1());
                configStaffFilter.setStartWorkingTime2(workingTimeConfig.getStartWorkingTime2());
                configStaffFilter.setEndWorkingTime2(workingTimeConfig.getEndWorkingTime2());
                configStaffFilter.setWorkingTimeShiftType(workingTimeConfig.getShiftType());
                configStaffFilter.setNumWorkingHour(workingTimeConfig.getNumWorkingHours());
                configStaffFilter.setNumDayOffInMonth(workingTimeConfig.getNumDayOffInMonth());
            }
            configStaffFilter.setSalaryInputType(UserSalaryInputType.MONTH.getValue());
            configStaffFilter.setSalaryNumber(new BigDecimal(10000000));
            String month = DateUtility.formatDate(sysdate, DateUtility.FORMAT_DATE_SQL);
            configStaffFilter.setValidFrom(month);
            this.configStaff(user.getId(), user.getId(), configStaffFilter);
        }
        UserDTO userDTO = new UserDTO(user);
        userDTO.setCompany(this.getCompanyDetail(company.getId()));
        userDTO.setBranch(this.getBranch(user.getId(), user.getCompany().getId(), AppConstant.APP_USER));
        return userDTO;
    }

    private NameModel handleName(String fullName) {
        NameModel nameModel = new NameModel();
        String[] names = fullName.split(" ");
        if (names.length == 1) {
            nameModel.setLastName(fullName);
        } else if (names.length > 1) {
            int sizeNames = names.length;
            int nameLats = sizeNames - 1;
            String firstName = "";
            for (int i = 0; i < sizeNames; i++) {
                if (i == nameLats) {
                    nameModel.setLastName(names[nameLats]);
                } else {
                    firstName = firstName + names[i] + " ";
                }
            }
            nameModel.setFirstName(firstName.trim());
        }
        return nameModel;
    }

    @Override
    public Boolean viewNotification(UserViewNotification model, UserInfo userInfo) throws BusinessException {
        LoggingInfo loggingInfo = initialize(userInfo);
        model.getNotificationIds().forEach(item -> {
            Optional<Notification> notificationOptional = notificationRepo.findById(item);
            if (notificationOptional.isPresent()) {
                Notification notification = notificationOptional.get();
                notification.setIsSeen(true);
                notificationRepo.save(notification);
            }
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public User logMeInWithSocial(CreateUserModel model, LoginType loginType) {
        return logMeSocialWithType(model, loginType);
    }

    private User logMeSocialWithType(CreateUserModel model, LoginType socialType) {
        if (StringUtility.isEmpty(model.getPhone())) {
            if (StringUtility.isNotEmpty(model.getSocialId())) {
                User userBySocialId = userRepo.getUserBySocialId(model.getSocialId(), socialType);
                if (userBySocialId == null) {
                    return createUserWithSocialInformation(model, socialType);
                } else {
                    return userBySocialId;
                }
            }
        } else {
            User userByPhone = userRepo.getUserByPhone(
                    model.getPhone(),
                    StatusType.DELETE.getValue(),
                    UserType.NORMAL_USERS.getValue());
            if (userByPhone != null) {
                return updateAccountWithSocialInformation(userByPhone, model, socialType);
            }
        }
        return null;
    }

    /**
     * Update info account social
     *
     * @param user
     * @param model
     */
    private User updateAccountWithSocialInformation(User user, CreateUserModel model, LoginType socialType) {
        initialize();
        User userBySocialId;
        if (LoginType.FACEBOOK == socialType) {
            if (model.getSocialId() != null) {
                userBySocialId = userRepo.getUserBySocialId(model.getSocialId(), socialType);
                if (userBySocialId == null) {
                    user.setFbId(model.getSocialId());
                } else {
                    if (StringUtility.isEmpty(userBySocialId.getPhone())) {
                        user.setFbId(model.getSocialId());
                        userRepo.delete(userBySocialId);
                        return user;
                    }
                    return null;
                }
            } else {
                user.setFbId(null);
            }
        } else if (LoginType.GOOGLE == socialType) {
            if (model.getSocialId() != null) {
                userBySocialId = userRepo.getUserBySocialId(model.getSocialId(), socialType);
                if (userBySocialId == null) {
                    user.setGgId(model.getSocialId());
                } else {
                    if (StringUtility.isEmpty(userBySocialId.getPhone())) {
                        user.setGgId(model.getSocialId());
                        userRepo.delete(userBySocialId);
                        return user;
                    }
                    return null;
                }
            } else {
                user.setGgId(null);
            }
        }
        user.setGender(GenderType.parse(model.getGender()));
        user.setUpdatedAt(sysTimestamp);
        userRepo.save(user);
        return user;
    }

    /**
     * Create user with social information
     *
     * @param model
     * @param socialType
     * @return
     */
    private User createUserWithSocialInformation(CreateUserModel model, LoginType socialType) {
        initialize();
        User user = new User();
        if (LoginType.FACEBOOK == socialType) {
            user.setFbId(model.getSocialId());
        } else if (LoginType.GOOGLE == socialType) {
            user.setGgId(model.getSocialId());
        }
        user.setName(model.getName());
        user.setGender(GenderType.parse(model.getGender()));
        user.setPhone(model.getPhone());
        user.setAvatarPath(model.getAvatarPath());
        user.setBirthDate(sysTimestamp);
        user.setStatus(StatusType.DRAFT);
        user.setCreatedAt(sysTimestamp);
        user.setUserType(UserType.NORMAL_USERS);
        user = userRepo.save(user);
        return user;
    }

    @Override
    public String uploadAvatar(MultipartFile file, Long userId) {
        String filePath = "";
        ;
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            filePath = commonBus.uploadFile(file, "user/" + userId + "/profile/avatar", user.getCompany().getIdAlias());
        }
        return filePath;
    }

    @Override
    public String uploadUserResource(MultipartFile file, Integer imageSide, Long userId) {
        initialize();
        Optional<User> userOptional = userRepo.findById(userId);
        String filePath = "";
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResource userResource = userResourceRepo
                    .getUserResourceBySide(userId, UserResourceType.PERSONAL_FILE.getValue(), imageSide);
            filePath = commonBus.uploadFile(file, "user/" + userId + "/personal", user.getCompany().getIdAlias());
            if (userResource != null) {
                userResource.setPathToResource(filePath);
                userResource.setUpdatedBy(user);
                userResource.setUpdatedAt(sysTimestamp);
                userResourceRepo.save(userResource);
            } else {
                UserResource resource = new UserResource();
                resource.setUser(user);
                resource.setType(UserResourceType.PERSONAL_FILE);
                if (imageSide == UserResourceImageSideType.FRONT_SIDE.getValue()) {
                    resource.setImageSide(UserResourceImageSideType.FRONT_SIDE);
                } else if (imageSide == UserResourceImageSideType.BACK_SIDE.getValue()) {
                    resource.setImageSide(UserResourceImageSideType.BACK_SIDE);
                }
                resource.setPathToResource(filePath);
                resource.setStatus(StatusType.ACTIVE);
                resource.setCreatedBy(userOptional.get());
                resource.setCreatedAt(sysTimestamp);
                userResourceRepo.save(resource);
            }
        }
        return filePath;
    }

    @Override
    public OTPModel sendOTP(OTPFilter filter) throws BusinessException {
//        if (StringUtility.isNotEmpty(filter.getPhone())) {
//            RestTemplate restTemplate = new RestTemplate();
//            String otpCode = StringUtility.generateRandomNumber(4);
//            String contentSMS = null;
//            if (filter.getSendType() == OTPType.REGISTER.getValue()) {
//                contentSMS = otpCode + " - Ma xac thuc (OTP) de kich hoat ung dung SUAXE411. " +
//                        "Vui long khong cung cap cho bat ky ai.";
//            } else if (filter.getSendType() == OTPType.FORGOT_PASS.getValue()) {
//                contentSMS = otpCode + " - Ma xac thuc (OTP) de lay lai mat khau tren ung dung SUAXE411. " +
//                        "Vui long khong cung cap cho bat ky ai.";
//            }
//            String url = String.format("http://124.158.14.49/CMC_BRAND/Service" +
//                            ".asmx/SendSMSBrandName?phone=%s&sms=%s&sender=%s&username=%s&password=%s",
//                    filter.getPhone(), contentSMS, AppConstant.SENDER, AppConstant.USER_NAME, AppConstant.PASSWORD);
//            String result = restTemplate.getForObject(url, String.class);
//            String status = "";
//            try {
//                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//                InputSource src = new InputSource();
//                src.setCharacterStream(new StringReader(result));
//                Document doc = builder.parse(src);
//                status = doc.getElementsByTagName("string").item(0).getTextContent();
//            } catch (ParserConfigurationException | SAXException | IOException e) {
//                e.printStackTrace();
//            }
//            if (StringUtility.isNotEmpty(status)) {
//                OTPModel otpModel = new OTPModel();
//                if (status.equals("1")) {
//                    otpModel.setCodeOTP(otpCode);
//                } else {
//                    otpModel.setCodeOTP("");
//                }
//                otpModel.setEndAt(new Timestamp(new Date().getTime() + (5 * 60 * 1000)));
//                otpModel.setStatus(status);
//                return otpModel;
//            }
//        }
        return new OTPModel("1234");
    }

    @Override
    public Boolean confirmOTP(OTPFilter filter, Long userId) throws BusinessException {
        User user = getUser(userId);
        if (user != null) {
            user.setStatus(StatusType.ACTIVE);
            user.setPhone(filter.getPhone());
            userRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean changePassByEmail(String email, String newPass) {
        User user = userRepo.getUserByEmail(
                email,
                StatusType.DELETE.getValue(),
                UserType.NORMAL_USERS.getValue());
        if (user != null) {
            user.setPassword(newPass);
            userRepo.save(user);
        }
        return true;
    }

    @Override
    public UserDevice registerDeviceInfo(UserDeviceModel model, UserInfo userInfo, String platform) throws BusinessException {
        initialize();
        Long userId = userInfo.getUserId();
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            UserDevice deviceExist = userDeviceRepo.findRowExist(model.getDeviceToken());
            if (deviceExist != null) {
                // check case: if userId in DB == userId current in app and token in DB == token current
                // return Delete row with userId in DB and insert new row with userId current
                userDeviceRepo.delete(deviceExist);
                insertUserDevice(user.get(), model, platform);
            } else {
                insertUserDevice(user.get(), model, platform);
            }
        }
        return null;
    }

    /**
     * Insert user device
     *
     * @param user
     * @param model
     * @param platform
     * @return
     */
    private UserDevice insertUserDevice(User user, UserDeviceModel model, String platform) {
        UserDevice device = new UserDevice();
        device.setUser(user);
        device.setDeviceId(model.getDeviceId());
        device.setDeviceToken(model.getDeviceToken());
        device.setStatus(StatusType.ACTIVE);
        device.setCreatedBy(user);
        device.setCreatedAt(sysTimestamp);
        if (StringUtility.isNotEmpty(platform) && platform.equals("android")) {
            device.setOsType(PlatformType.ANDROID);
        } else {
            device.setOsType(PlatformType.IOS);
        }
        return userDeviceRepo.save(device);
    }

    @Override
    public ErrorCode deleteUserDevice(UserInfo userInfo, String deviceToken) throws BusinessException {
        UserDevice deviceFindUserAndToken = userDeviceRepo.getUserDevice(userInfo.getUserId(), deviceToken);
        if (deviceFindUserAndToken != null) {
            userDeviceRepo.delete(deviceFindUserAndToken);
        }
        return ErrorCode.NO_ERROR;
    }

    @Override
    public List<ConversationModel> getConversation(ConversationFilter filter, UserInfo userInfo) throws BusinessException {
        List<Long> conversations = filter.getConversationIds();
        List<ConversationModel> models = new ArrayList<>();
        if (conversations.size() != 0) {
            conversations.forEach(id -> {
                Conversation conversation = conversationRepo.getConversationById(id, userInfo.getUserId(), StatusType.DELETE.getValue());
                ConversationMember conversationMember = conversationMemberRepo.getMemberChatInConversation(id, userInfo.getUserId());
                if (conversation != null) {
                    ConversationModel model = new ConversationModel(conversation);
                    if (conversationMember != null) {
                        model.setName(conversationMember.getConversationName());
                    }
                    models.add(model);
                }
            });
        }
        return models;
    }

    @Override
    public List<ConversationMemberModel> getMemberOfConversation(ConversationFilter filter, UserInfo userInfo) throws BusinessException {
        List<Long> conversations = filter.getConversationIds();
        List<ConversationMemberModel> models = new ArrayList<>();
        if (conversations.size() != 0) {
            conversations.forEach(id -> {
                ConversationMember conversationMember =
                        conversationMemberRepo.getMemberChatInConversation(id, userInfo.getUserId());
                if (conversationMember != null) {
                    Optional<User> userOptional = userRepo.findById(conversationMember.getUser().getId());
                    if (userOptional.isPresent()) {
                        ConversationMemberModel model = new ConversationMemberModel(userOptional.get());
                        model.setConversationId(conversationMember.getConversation().getId());
                        model.setTokenImage(generateTokenWithConversationId(conversationMember.getConversation().getId()));
                        Optional<Conversation> conversationOptional = conversationRepo.findById(id);
                        conversationOptional.ifPresent(conversation -> model.setStatusConversation(conversation.getStatus()));
                        models.add(model);
                    }
                }
            });
        }
        return models;
    }

    private String generateTokenWithConversationId(Long conversationId) {
        String token = "abcd" + conversationId;
        return token;
    }

    @Override
    @Transactional
    public ConversationMemberModel createConversation(ConversationFilter filter, UserInfo userInfo) {
        if (filter.getUserMemberChatId() != null || filter.getUserInvited() != null) {
            int numberMemberInConversation = filter.getUserInvited() != null ? filter.getUserInvited().size() : 2;
            Optional<User> userChatOptional = null;
            if (filter.getUserMemberChatId() != null) {
                userChatOptional = userRepo.findById(filter.getUserMemberChatId());
            }
            if ((userChatOptional != null && userChatOptional.isPresent()) || filter.getUserInvited() != null) {
                initialize();
                User userChat = null;
                if (userChatOptional != null && userChatOptional.isPresent()) {
                    userChat = userChatOptional.get();
                }
                Conversation conversation = new Conversation();
                conversation.setCreatedAt(sysTimestamp);
                if (filter.getAvatarGroup() != null) {
                    conversation.setAvatarPath(filter.getAvatarGroup());
                }
                conversation.setName(filter.getName() != null ? filter.getName() : userChat.getName());
                conversation.setStatus(ConversationStatus.ACTIVE);
                conversationRepo.save(conversation);
                Conversation conversationSaved = conversationRepo.save(conversation);
                Optional<User> me = userRepo.findById(userInfo.getUserId());
                if (me.isPresent()) {
                    for (int i = 0; i < numberMemberInConversation; i++) {
                        ConversationMember conversationMember = new ConversationMember();
                        conversationMember.setConversation(conversationSaved);
                        if (filter.getUserInvited() != null) {
                            if (i == 0) {
                                ConversationMember conversationAdmin = new ConversationMember();
                                conversationAdmin.setConversation(conversationSaved);
                                me.ifPresent(conversationAdmin::setUser);
                                conversationAdmin.setConversationName(filter.getName() != null ? filter.getName() : me.get().getName());
                                conversationAdmin.setDeletedConversation(false);
                                conversationMemberRepo.save(conversationAdmin);
                            }
                            conversationMember.setConversationName(filter.getName() != null ? filter.getName() : userRepo.findById(filter.getUserInvited().get(i)).get().getName());
                            conversationMember.setUser(userRepo.findById(filter.getUserInvited().get(i)).get());
                        } else {
                            if (i == 0) {
                                me.ifPresent(conversationMember::setUser);
                                conversationMember.setConversationName(userChat.getName());
                            } else {
                                conversationMember.setUser(userChat);
                                conversationMember.setConversationName(me.get().getName());
                            }
                        }
                        conversationMember.setDeletedConversation(false);
                        conversationMemberRepo.save(conversationMember);
                    }
                }
                ConversationMemberModel model = new ConversationMemberModel(me.get());
                model.setTokenImage(generateTokenWithConversationId(conversationSaved.getId()));
                model.setConversationId(conversationSaved.getId());
                Optional<Conversation> conversationOptional = conversationRepo.findById(conversationSaved.getId());
                conversationOptional.ifPresent(conversation1 -> model.setStatusConversation(conversation1.getStatus()));
                // add user group into firebase
                ArrayList<Long> userIds = new ArrayList<>();
                userIds.add(me.get().getId());
                if (filter.getUserInvited() != null) {
                    userIds.addAll(filter.getUserInvited());
                } else {
                    userIds.add(userChat.getId());
                }
                // create path /members/c{conversationId}
                DatabaseReference refMe = FirebaseDatabase.getInstance().getReference();
                if (userChat != null) {
                    refMe.child("members/c" + conversationSaved.getId().toString() + "/u" + me.get().getId().toString())
                            .setValueAsync(new Members(false, userChat.getName(), 0));
                    DatabaseReference refMemChat = FirebaseDatabase.getInstance().getReference();
                    refMemChat.child("members/c" + conversationSaved.getId().toString() + "/u" + userChat.getId().toString())
                            .setValueAsync(new Members(false, me.get().getName(), 0));
                } else if (filter.getUserInvited() != null) {
                    DatabaseReference refMemChat = FirebaseDatabase.getInstance().getReference();
                    for (Long userId : userIds) {
                        refMemChat.child("members/c" + conversationSaved.getId().toString() + "/u" + userId)
                                .setValueAsync(new Members(false, filter.getName() != null ? filter.getName() : me.get().getName(), 0));
                    }
                }
                // create path /conversation
                DatabaseReference refConversation = FirebaseDatabase.getInstance().getReference();
                refConversation.child("conversation/c" + conversationSaved.getId() + "/deleted").setValueAsync(false);
                // Path chats_by_user
                Long timestamp = Calendar.getInstance().getTimeInMillis();
                for (Long userId : userIds) {
                    DatabaseReference refChatsByUser = FirebaseDatabase.getInstance().getReference();
                    refChatsByUser.child("chats_by_user/u" + userId + "/_conversation/c" + conversationSaved.getId() +
                            "/deleted").setValueAsync(false);
                    refChatsByUser.child("chats_by_user/u" + userId + "/_conversation/c" + conversationSaved.getId() +
                            "/last_updated_at").setValueAsync(timestamp);
                    refChatsByUser.child("chats_by_user/u" + userId + "/_conversation/c" + conversationSaved.getId() +
                            "/deleted__last_updated_at").setValueAsync(1 + "_" + timestamp);
                    refChatsByUser.child("chats_by_user/u" + userId + "/_conversation/c" + conversationSaved.getId() +
                            "/last_messages").setValueAsync(new LastMessage(filter.getContent(),
                            filter.getTypeMessage(),
                            timestamp));
                    refChatsByUser.child("chats_by_user/u" + userId + "/_all_conversation").setValueAsync(new AllConversation(
                            conversationSaved.getId(),
                            userId,
                            timestamp,
                            new LastMessage(filter.getContent(), filter.getTypeMessage(), timestamp)
                    ));
                    // increase number unseen for conversation:
                    DatabaseReference refNumberUnseenOfConversation = FirebaseDatabase.getInstance().getReference();
                    if ((filter.getUserInvited() != null) || (!userId.equals(me.get().getId()) && filter.getUserMemberChatId() != null)) {
                        refNumberUnseenOfConversation.child("members/c" + conversationSaved.getId() + "/u" + userId +
                                "/number_of_unseen_messages").runTransaction(new Transaction.Handler() {
                            @Override
                            public Transaction.Result doTransaction(MutableData mutableData) {
                                Integer numberOfUnseen = mutableData.getValue(Integer.TYPE);
                                if (numberOfUnseen == null) {
                                    numberOfUnseen = 1;
                                } else {
                                    numberOfUnseen += 1;
                                }
                                // Set value and report transaction success
                                mutableData.setValue(numberOfUnseen);
                                return Transaction.success(mutableData);
                            }

                            @Override
                            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                // Transaction completed
                            }
                        });
                        // increase number unseen for user
                        DatabaseReference refNumberUnseenOfUser = FirebaseDatabase.getInstance().getReference();
                        refNumberUnseenOfUser.child("chats_by_user/u" + userId +
                                "/number_of_unseen_messages").runTransaction(new Transaction.Handler() {
                            @Override
                            public Transaction.Result doTransaction(MutableData mutableData) {
                                Integer numberOfUnseen = mutableData.getValue(Integer.TYPE);
                                if (numberOfUnseen == null) {
                                    numberOfUnseen = 1;
                                } else {
                                    numberOfUnseen += 1;
                                }
                                // Set value and report transaction success
                                mutableData.setValue(numberOfUnseen);
                                return Transaction.success(mutableData);
                            }

                            @Override
                            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                // Transaction completed
                            }
                        });
                    }
                }

                // push message:
                DatabaseReference refMessageByUser = FirebaseDatabase.getInstance().getReference();
                String keyMessage =
                        refMessageByUser.child("messages_by_conversation/c" + conversationSaved.getId()).push().getKey();
                DatabaseReference sendMessageRef = FirebaseDatabase.getInstance().getReference();
                sendMessageRef.child("messages_by_conversation/c" + conversationSaved.getId().toString() + "/" + keyMessage)
                        .setValueAsync(new MessageByConversation(
                                me.get().getId(),
                                timestamp,
                                true,
                                0,
                                filter.getTypeMessage(),
                                filter.getContent()
                        ));
                return model;
            }
        }
        return null;
    }

    @Override
    public String uploadResourceConversation(MultipartFile file, Long conversationId, Long userId) {
        String filePath = "";
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            filePath = commonBus.uploadFile(file, "conversation/" + conversationId, user.getCompany().getIdAlias());
        }
        return filePath;
    }

    @Override
    public String uploadAvatarConversation(MultipartFile file, Long conversationId, Long userId) {
        String filePath = "";
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            filePath = commonBus.uploadFile(file, "conversation/" + conversationId + "/group", user.getCompany().getIdAlias());
        }
        return filePath;
    }

    @Override
    @Transactional
    public Boolean deleteConversation(Long conversationId, UserInfo userInfo) throws BusinessException {
        Optional<User> meOptional = userRepo.findById(userInfo.getUserId());
        if (meOptional.isPresent()) {
            initialize();
            User me = meOptional.get();
            Optional<Conversation> conversationOptional = conversationRepo.findById(conversationId);
            if (conversationOptional.isPresent()) {
                Conversation conversation = conversationOptional.get();
                if (conversation.getStatus() == ConversationStatus.SUSPEND) {
                    Integer memberCurrent = conversationMemberRepo.checkMemberCurrentConversation(conversationId);
                    if (memberCurrent <= 1) {
                        conversation.setStatus(ConversationStatus.DELETE);
                        conversationRepo.save(conversation);
                    }

                } else if (conversation.getStatus() == ConversationStatus.ACTIVE) {
                    conversation.setStatus(ConversationStatus.SUSPEND);
                    conversationRepo.save(conversation);
                }
                List<ConversationMember> conversationMembers =
                        conversationMemberRepo.getConversationMemberById(conversationId);
                SetStatusDeletedConversationMember(me, conversationMembers);
                Long timestamp = Calendar.getInstance().getTimeInMillis();
                DatabaseReference refChatsByUser = FirebaseDatabase.getInstance().getReference();
                for (ConversationMember conversationMember : conversationMembers) {
                    refChatsByUser.child("chats_by_user/u" + conversationMember.getUser().getId() + "/_conversation/c" + conversationId +
                            "/deleted").setValueAsync(false);
                    refChatsByUser.child("chats_by_user/u" + conversationMember.getUser().getId() + "/_conversation/c" + conversationId +
                            "/last_updated_at").setValueAsync(timestamp);
                    refChatsByUser.child("chats_by_user/u" + conversationMember.getUser().getId() + "/_conversation/c" + conversationId +
                            "/deleted__last_updated_at").setValueAsync(1 + "_" + timestamp);
                    refChatsByUser.child("chats_by_user/u" + conversationMember.getUser().getId() + "/_conversation/c" + conversationId +
                            "/last_messages").setValueAsync(new LastMessage(me.getName() + " đã rời khỏi cuộc hội thoại",
                            3,
                            timestamp));
                    refChatsByUser.child("chats_by_user/u" + conversationMember.getUser().getId() + "/_all_conversation").setValueAsync(new AllConversation(
                            conversationId,
                            conversationMember.getUser().getId(),
                            timestamp,
                            new LastMessage(me.getName() + " đã rời khỏi cuộc hội thoại", 3, timestamp)
                    ));
                    // increase number unseen for conversation:
                    DatabaseReference refNumberUnseenOfConversation = FirebaseDatabase.getInstance().getReference();
                    refNumberUnseenOfConversation.child("members/c" + conversationId + "/u" + conversationMember.getUser().getId() +
                            "/number_of_unseen_messages").runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            Integer numberOfUnseen = mutableData.getValue(Integer.TYPE);
                            if (numberOfUnseen == null) {
                                numberOfUnseen = 1;
                            } else {
                                numberOfUnseen += 1;
                            }
                            // Set value and report transaction success
                            mutableData.setValue(numberOfUnseen);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                            // Transaction completed
                        }
                    });
                    // increase number unseen for user
                    DatabaseReference refNumberUnseenOfUser = FirebaseDatabase.getInstance().getReference();
                    refNumberUnseenOfUser.child("chats_by_user/u" + conversationMember.getUser().getId() +
                            "/number_of_unseen_messages").runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            Integer numberOfUnseen = mutableData.getValue(Integer.TYPE);
                            if (numberOfUnseen == null) {
                                numberOfUnseen = 1;
                            } else {
                                numberOfUnseen += 1;
                            }
                            // Set value and report transaction success
                            mutableData.setValue(numberOfUnseen);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                            // Transaction completed
                        }
                    });
                }
                // push message:
                DatabaseReference refMessageByUser = FirebaseDatabase.getInstance().getReference();
                String keyMessage =
                        refMessageByUser.child("messages_by_conversation/c" + conversationId).push().getKey();
                DatabaseReference sendMessageRef = FirebaseDatabase.getInstance().getReference();
                sendMessageRef.child("messages_by_conversation/c" + conversationId + "/" + keyMessage)
                        .setValueAsync(new MessageByConversation(
                                userInfo.getUserId(),
                                timestamp,
                                true,
                                0,
                                3,
                                me.getName() + " đã rời khỏi cuộc hội thoại"
                        ));
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean editConversation(Long conversationId, ConversationFilter filter, UserInfo userInfo) throws BusinessException {
        initialize();
        Optional<Conversation> conversationOptional = conversationRepo.findById(conversationId);
        if (filter.getUserInvited() == null) {
            if (conversationOptional.isPresent()) {
                Conversation conversation = conversationOptional.get();
                if (filter.getName() != null) {
                    conversation.setName(filter.getName());
                }
                if (filter.getAvatarGroup() != null) {
                    conversation.setAvatarPath(filter.getAvatarGroup());
                }
                List<ConversationMember> conversationMember = conversationMemberRepo.getConversationMemberById(conversation.getId());
                if (conversationMember.size() > 0) {
                    conversationMember.forEach(conversationMemberUser -> {
                        if (filter.getName() != null) {
                            conversationMemberUser.setConversationName(filter.getName());
                            conversationMemberRepo.save(conversationMemberUser);
                            DatabaseReference refMemChat = FirebaseDatabase.getInstance().getReference();
                            refMemChat.child("members/c" + conversationId + "/u" + conversationMemberUser.getUser().getId() + "/name").setValueAsync(filter.getName());
                        }
                        // create path chat by user
                        DatabaseReference refChatsByUser = FirebaseDatabase.getInstance().getReference();
                        Long timestamp = Calendar.getInstance().getTimeInMillis();
                        refChatsByUser.child("chats_by_user/u" + conversationMemberUser.getUser().getId() + "/_conversation/c" + conversationMemberUser.getConversation().getId().toString() +
                                "/deleted").setValueAsync(false);
                        refChatsByUser.child("chats_by_user/u" + conversationMemberUser.getUser().getId() + "/_conversation/c" + conversationMemberUser.getConversation().getId().toString() +
                                "/last_updated_at").setValueAsync(timestamp);
                        refChatsByUser.child("chats_by_user/u" + conversationMemberUser.getUser().getId() + "/_conversation/c" + conversationMemberUser.getConversation().getId().toString() +
                                "/deleted__last_updated_at").setValueAsync(1 + "_" + timestamp);
                        refChatsByUser.child("chats_by_user/u" + conversationMemberUser.getUser().getId() + "/_conversation/c" + conversationMemberUser.getConversation().getId().toString() +
                                "/last_messages").setValueAsync(new LastMessage(filter.getContent(),
                                filter.getTypeMessage(),
                                timestamp));
                        refChatsByUser.child("chats_by_user/u" + conversationMemberUser.getUser().getId() + "/_all_conversation").setValueAsync(new AllConversation(
                                conversationMemberUser.getConversation().getId(),
                                conversationMemberUser.getUser().getId(),
                                timestamp,
                                new LastMessage(filter.getContent(), filter.getTypeMessage(), timestamp)
                        ));
                        // increase number unseen for conversation:
                        DatabaseReference refNumberUnseenOfConversation = FirebaseDatabase.getInstance().getReference();
                        refNumberUnseenOfConversation.child("members/c" + conversationId + "/u" + conversationMemberUser.getUser().getId() +
                                "/number_of_unseen_messages").runTransaction(new Transaction.Handler() {
                            @Override
                            public Transaction.Result doTransaction(MutableData mutableData) {
                                Integer numberOfUnseen = mutableData.getValue(Integer.TYPE);
                                if (numberOfUnseen == null) {
                                    numberOfUnseen = 1;
                                } else {
                                    numberOfUnseen += 1;
                                }
                                // Set value and report transaction success
                                mutableData.setValue(numberOfUnseen);
                                return Transaction.success(mutableData);
                            }

                            @Override
                            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                // Transaction completed
                            }
                        });
                        // increase number unseen for user
                        DatabaseReference refNumberUnseenOfUser = FirebaseDatabase.getInstance().getReference();
                        refNumberUnseenOfUser.child("chats_by_user/u" + conversationMemberUser.getUser().getId() +
                                "/number_of_unseen_messages").runTransaction(new Transaction.Handler() {
                            @Override
                            public Transaction.Result doTransaction(MutableData mutableData) {
                                Integer numberOfUnseen = mutableData.getValue(Integer.TYPE);
                                if (numberOfUnseen == null) {
                                    numberOfUnseen = 1;
                                } else {
                                    numberOfUnseen += 1;
                                }
                                // Set value and report transaction success
                                mutableData.setValue(numberOfUnseen);
                                return Transaction.success(mutableData);
                            }

                            @Override
                            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                // Transaction completed
                            }
                        });
                    });
                    // push message:
                    DatabaseReference refMessageByUser = FirebaseDatabase.getInstance().getReference();
                    String keyMessage =
                            refMessageByUser.child("messages_by_conversation/c" + conversationId).push().getKey();
                    DatabaseReference sendMessageRef = FirebaseDatabase.getInstance().getReference();
                    sendMessageRef.child("messages_by_conversation/c" + conversationId + "/" + keyMessage)
                            .setValueAsync(new MessageByConversation(
                                    userInfo.getUserId(),
                                    Calendar.getInstance().getTimeInMillis(),
                                    true,
                                    0,
                                    filter.getTypeMessage(),
                                    filter.getContent()
                            ));
                }
                conversationRepo.save(conversation);
                return true;
            }
        } else {
            if (conversationOptional.isPresent()) {
                ConversationMember conversationMemberUser = conversationMemberRepo.getMemberChatInConversation(conversationId, userInfo.getUserId());
                DatabaseReference refMemChat = FirebaseDatabase.getInstance().getReference();
                for (int i = 0; i < filter.getUserInvited().size(); i++) {
                    ConversationMember checkExitMember = conversationMemberRepo.getMemberChatInConversation(conversationId, filter.getUserInvited().get(i));
                    if (checkExitMember != null) {
                        if (checkExitMember.getDeletedConversation()) {
                            checkExitMember.setDeletedConversation(false);
                            conversationMemberRepo.save(checkExitMember);
                        }
                    } else {
                        ConversationMember conversationMember = new ConversationMember();
                        conversationMember.setConversation(conversationMemberUser.getConversation());
                        conversationMember.setUser(userRepo.findById(filter.getUserInvited().get(i)).get());
                        conversationMember.setConversationName(conversationMemberUser.getConversationName());
                        conversationMember.setDeletedConversation(false);
                        conversationMemberRepo.save(conversationMember);
                        // create path member
                        refMemChat.child("members/c" + conversationMemberUser.getConversation().getId().toString() + "/u" + filter.getUserInvited().get(i).toString())
                                .setValueAsync(new Members(false, userInfo.getUserId().toString(), 0));
                    }
                    // create path chat by user
                    DatabaseReference refChatsByUser = FirebaseDatabase.getInstance().getReference();
                    Long timestamp = Calendar.getInstance().getTimeInMillis();
                    if (i == 0) {
                        refChatsByUser.child("chats_by_user/u" + userInfo.getUserId() + "/_conversation/c" + conversationMemberUser.getConversation().getId().toString() +
                                "/deleted").setValueAsync(false);
                        refChatsByUser.child("chats_by_user/u" + userInfo.getUserId() + "/_conversation/c" + conversationMemberUser.getConversation().getId().toString() +
                                "/last_updated_at").setValueAsync(timestamp);
                        refChatsByUser.child("chats_by_user/u" + userInfo.getUserId() + "/_conversation/c" + conversationMemberUser.getConversation().getId().toString() +
                                "/deleted__last_updated_at").setValueAsync(1 + "_" + timestamp);
                        refChatsByUser.child("chats_by_user/u" + userInfo.getUserId() + "/_conversation/c" + conversationMemberUser.getConversation().getId().toString() +
                                "/last_messages").setValueAsync(new LastMessage(filter.getContent(),
                                filter.getTypeMessage(),
                                timestamp));
                        refChatsByUser.child("chats_by_user/u" + userInfo.getUserId() + "/_all_conversation").setValueAsync(new AllConversation(
                                conversationMemberUser.getConversation().getId(),
                                userInfo.getUserId(),
                                timestamp,
                                new LastMessage(filter.getContent(), filter.getTypeMessage(), timestamp)
                        ));
                    }
                    refChatsByUser.child("chats_by_user/u" + filter.getUserInvited().get(i).toString() + "/_conversation/c" + conversationMemberUser.getConversation().getId().toString() +
                            "/deleted").setValueAsync(false);
                    refChatsByUser.child("chats_by_user/u" + filter.getUserInvited().get(i).toString() + "/_conversation/c" + conversationMemberUser.getConversation().getId().toString() +
                            "/last_updated_at").setValueAsync(timestamp);
                    refChatsByUser.child("chats_by_user/u" + filter.getUserInvited().get(i).toString() + "/_conversation/c" + conversationMemberUser.getConversation().getId().toString() +
                            "/deleted__last_updated_at").setValueAsync(1 + "_" + timestamp);
                    refChatsByUser.child("chats_by_user/u" + filter.getUserInvited().get(i).toString() + "/_conversation/c" + conversationMemberUser.getConversation().getId().toString() +
                            "/last_messages").setValueAsync(new LastMessage(filter.getContent(),
                            filter.getTypeMessage(),
                            timestamp));
                    refChatsByUser.child("chats_by_user/u" + filter.getUserInvited().get(i).toString() + "/_all_conversation").setValueAsync(new AllConversation(
                            conversationMemberUser.getConversation().getId(),
                            userInfo.getUserId(),
                            timestamp,
                            new LastMessage(filter.getContent(), filter.getTypeMessage(), timestamp)
                    ));
                }
                // set number unseen
                List<ConversationMember> conversationMemberList = conversationMemberRepo.getConversationMemberById(conversationId);
                for (ConversationMember conversationMember : conversationMemberList) {
                    // increase number unseen for conversation:
                    DatabaseReference refNumberUnseenOfConversation = FirebaseDatabase.getInstance().getReference();
                    refNumberUnseenOfConversation.child("members/c" + conversationId + "/u" + conversationMember.getUser().getId() +
                            "/number_of_unseen_messages").runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            Integer numberOfUnseen = mutableData.getValue(Integer.TYPE);
                            if (numberOfUnseen == null) {
                                numberOfUnseen = 1;
                            } else {
                                numberOfUnseen += 1;
                            }
                            // Set value and report transaction success
                            mutableData.setValue(numberOfUnseen);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                            // Transaction completed
                        }
                    });
                    // increase number unseen for user
                    DatabaseReference refNumberUnseenOfUser = FirebaseDatabase.getInstance().getReference();
                    refNumberUnseenOfUser.child("chats_by_user/u" + conversationMember.getUser().getId() +
                            "/number_of_unseen_messages").runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            Integer numberOfUnseen = mutableData.getValue(Integer.TYPE);
                            if (numberOfUnseen == null) {
                                numberOfUnseen = 1;
                            } else {
                                numberOfUnseen += 1;
                            }
                            // Set value and report transaction success
                            mutableData.setValue(numberOfUnseen);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                            // Transaction completed
                        }
                    });
                }
                // push message:
                DatabaseReference refMessageByUser = FirebaseDatabase.getInstance().getReference();
                String keyMessage =
                        refMessageByUser.child("messages_by_conversation/c" + conversationId).push().getKey();
                DatabaseReference sendMessageRef = FirebaseDatabase.getInstance().getReference();
                sendMessageRef.child("messages_by_conversation/c" + conversationId + "/" + keyMessage)
                        .setValueAsync(new MessageByConversation(
                                userInfo.getUserId(),
                                Calendar.getInstance().getTimeInMillis(),
                                true,
                                0,
                                filter.getTypeMessage(),
                                filter.getContent()
                        ));
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ConversationMemberModel> searchConversation(ConversationFilter filter, UserInfo userInfo) throws BusinessException {
        if (filter.getParamsSearch() != null) {
            List<ConversationMember> conversationMembers = conversationMemberRepo.searchConversation(
                    StringUtility.convertUppercaseToLowercase(StringUtility.toFullSearchLike(StringUtility.removeAccent(filter.getParamsSearch()))),
                    userInfo.getUserId(),
                    ConversationStatus.DELETE.getValue());
            if (conversationMembers.size() > 0) {
                List<ConversationMemberModel> models = new ArrayList<>();
                for (ConversationMember conversationMember : conversationMembers) {
                    ConversationMemberModel model = new ConversationMemberModel(conversationMember.getUser());
                    model.setConversationId(conversationMember.getConversation().getId());
                    model.setTokenImage(generateTokenWithConversationId(conversationMember.getConversation().getId()));
                    Optional<Conversation> conversationOptical =
                            conversationRepo.findById(conversationMember.getConversation().getId());
                    conversationOptical.ifPresent(conversation -> model.setStatusConversation(conversation.getStatus()));
                    models.add(model);
                }
                return models;
            }
        }
        return null;
    }

    @Override
    public Long checkExistConversation(ConversationFilter filter, UserInfo userInfo) throws BusinessException {
        if (filter.getUserMemberChatId() != null) {
            Optional<User> me = userRepo.findById(userInfo.getUserId());
            if (me.isPresent()) {
                Optional<User> userChat = userRepo.findById(filter.getUserMemberChatId());
                if (userChat.isPresent()) {
                    return conversationMemberRepo.checkExistConversation(
                            userChat.get().getId(),
                            me.get().getId());
                }
            }
        }
        return null;
    }

    @Override
    public Boolean checkConversationActive(UserInfo userInfo) throws BusinessException {
        Optional<User> userOptional = userRepo.findById(userInfo.getUserId());
        if (userOptional.isPresent()) {
            Integer numberConversationActive =
                    conversationMemberRepo.checkConversationActive(userOptional.get().getId());
            return numberConversationActive > 0;
        }
        return false;
    }

    private void SetStatusDeletedConversationMember(User me, List<ConversationMember> conversationMembers) {
        if (conversationMembers != null) {
            if (conversationMembers.size() > 0) {
                for (ConversationMember cm : conversationMembers) {
                    if (cm.getUser() == me) {
                        cm.setDeletedConversation(true);
                        cm.setDeletedConversationAt(sysTimestamp);
                        conversationMemberRepo.save(cm);
                    }
                }
            }
        }
    }

    @Override
    public String getFirebaseTokenNew(UserInfo userInfo) throws BusinessException, FirebaseAuthException {
        Optional<User> user = userRepo.findById(userInfo.getUserId());
        String firebaseToken = "";
        if (user.isPresent()) {
//            Map<String, Object> additionalClaims = new HashMap<String, Object>();
//            additionalClaims.put("role", "student");

            firebaseToken = FirebaseAuth.getInstance().createCustomToken(String.valueOf(user.get().getId()));
        }
        return firebaseToken;
    }

    @Transactional
    @Override
    public Boolean resetPasswordAndSendNotifyEmail(String email, String newRawPassword, String encodedPassword) throws BusinessException {
        if (StringUtility.isEmpty(email)) {
            throw new IllegalArgumentException("email is invalid");
        }
        User user = userRepo.getUserByEmail(
                email.trim(),
                StatusType.DELETE.getValue(),
                UserType.NORMAL_USERS.getValue());
        if (user == null) {
            throw new BusinessException("user with email " + email + " does not exist");
        }
        user.setPassword(encodedPassword);
        userRepo.save(user);

        EmailModel model = new EmailModel();
        model.setTo(Optional.of(Arrays.asList(email.trim())));
        model.setSubject("Chấm công HD reset mật khẩu");

        try {
            File file = resetPasswordTemplateResource.getFile();
            byte[] bytes = Files.readAllBytes(file.toPath());
            String body = new String(bytes, "UTF-8");
            body = body.replace("{{username}}", user.getName()).replace("{{password}}", newRawPassword);
            model.setBody(body);
            emailHelper.sendEmail(model);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return true;
    }

    @Override
    public CompanyModel getCompanyDetail(Long companyId) throws BusinessException {
        Optional<Company> companyOptional = companyRepo.findById(companyId);
        CompanyModel companyModel = null;
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            companyModel = new CompanyModel(company);

            // Get list branches
            List<Branch> branches = branchRepo.getBranches(company.getId(), StatusType.ACTIVE.getValue());
            List<BranchModel> branchModels = branches.stream().map(item -> {
                return new BranchModel(item);
            }).collect(Collectors.toList());

            // Get list departments
            List<Department> departments = departmentRepo.getDepartments(company.getId(), StatusType.ACTIVE.getValue());
            List<DepartmentModel> departmentModels = departments.stream().map(item -> {
                return new DepartmentModel(item);
            }).collect(Collectors.toList());

            // Get list staffs
            List<Category> staffs = categoryRepo.getCategories(CategoryType.STAFF_TYPE.getValue(),
                    StatusType.ACTIVE.getValue(), company.getId());
            List<CategoryModel> staffModels = staffs.stream().map(item -> {
                return new CategoryModel(item);
            }).collect(Collectors.toList());
            companyModel.setBranches(branchModels);
            companyModel.setDepartments(departmentModels);
            companyModel.setStaffs(staffModels);
        }
        return companyModel;
    }

    @Override
    public BranchModel getBranch(Long userId, Long companyId, String bundleId) throws BusinessException {
        Branch branch;
        if (bundleId.equals(AppConstant.APP_ADMIN)) {
            branch = branchRepo.getBranchByAdminUserId(companyId, userId, StatusType.ACTIVE.getValue());
        } else {
            branch = branchRepo.getBranch(companyId, userId, StatusType.ACTIVE.getValue());
        }
        BranchModel branchModel = null;
        if (branch != null) {
            branchModel = new BranchModel(branch);
        }
        return branchModel;
    }

    @Override
    public Boolean deniedStaff(Long userId, Integer isStatus, Long userInfo) throws BusinessException {
        initialize();
        Optional<User> userOptional = userRepo.findById(userId);
        Optional<User> userInfoOptional = userRepo.findById(userInfo);
        if (userOptional.isPresent() && userInfoOptional.isPresent()) {
            User user = userOptional.get();
            User userAdmin = userInfoOptional.get();
            if (user.getStatus().getValue() == StatusType.DRAFT.getValue()) {
                user.setStatus(StatusType.DELETE);
            } else if (isStatus != null) {
                user.setStatus(StatusType.parse(isStatus));
            }
            user.setDeletedBy(userAdmin);
            user.setDeletedAt(sysTimestamp);
            userRepo.save(user);
            return true;
        }
        return false;
    }
}