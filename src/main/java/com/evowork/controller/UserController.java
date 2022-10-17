package com.evowork.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.evowork.business.UserBus;
import com.evowork.common.ErrorCode;
import com.evowork.config.AppConstant;
import com.evowork.entity.Company;
import com.evowork.entity.User;
import com.evowork.entity.UserDevice;
import com.evowork.enumeration.StatusType;
import com.evowork.enumeration.UserType;
import com.evowork.exception.BusinessException;
import com.evowork.response.ApiResponse;
import com.evowork.response.ErrorResponse;
import com.evowork.response.SuccessResponse;
import com.evowork.utility.BusinessUtility;
import com.evowork.utility.CacheUtility;
import com.evowork.utility.StringUtility;
import com.evowork.utility.TokenUtility;
import com.evowork.viewmodel.cache.UserInfo;
import com.evowork.viewmodel.company.CompanyFilter;
import com.evowork.viewmodel.conversation.ConversationFilter;
import com.evowork.viewmodel.department.DepartmentFilter;
import com.evowork.viewmodel.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tuannd
 * @date 09/03/2016
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    private UserBus userBus;

    private void saveLoginSessionToCache(UserDTO user) {
        cacheService.setValue(CacheUtility.createCacheUserKey(user.getId()), user.getToken());
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setUserIdentifier(BusinessUtility.getUserIdentifier(user));
        cacheService.setValue(user.getToken(), userInfo);
    }

    @PostMapping(path = "/log-me-in")
    public ApiResponse logMeIn(@RequestBody LogInModel model, HttpServletRequest request) {
        String bundleId = request.getHeader(AppConstant.HEADER_X_APP_TYPE);
        String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
        Integer userType;
        if (bundleId.equals(AppConstant.APP_ADMIN)) {
            userType = UserType.WEB_ADMIN.getValue();
        } else {
            userType = UserType.NORMAL_USERS.getValue();
        }
        try {
            User user = null;
            if (StringUtility.isNotEmpty(model.getEmail())) {
                user = userBus.getUserByEmail(model.getEmail(), userType);
            } else if (StringUtility.isNotEmpty(model.getPhone())) {
                user = userBus.getUserByPhone(model.getPhone(), userType);
            }
            if (user == null) {
                return new ErrorResponse(ErrorCode.INVALID_ACCOUNT);
            }
            // TODO: HACK for old users (don't have custom salt in encoded password), remove this hack when all users has changed password (or received changed password email, whatever)
            if (user.getPassword() != null) {
                if (passwordEncoder.matches(model.getPassword(), user.getPassword())) {
                    user.setPassword(encodePassword(model.getPassword()));
                    userBus.updateUser(user);
                } else if (!passwordMatch(model.getPassword(), user.getPassword()) && !model.getPassword().equals(user
                        .getPassword())) {
                    return new ErrorResponse(ErrorCode.LOGIN_FAIL_USERNAME_PASSWORD_MISMATCH);
                }
            } else {
                return new ErrorResponse(ErrorCode.LOGIN_FAIL_USERNAME_PASSWORD_MISMATCH);
            }
            if (user.getStatus() == StatusType.DRAFT) {
                return new ErrorResponse(ErrorCode.USER_WAITING_FOR_APPROVAL);
            } else if (user.getStatus() == StatusType.SUSPENDED) {
                return new ErrorResponse(ErrorCode.USER_HAS_BEEN_DELETED);
            }
            ErrorCode errorCode = ErrorCode.NO_ERROR;
            if (bundleId.equals(AppConstant.APP_USER)
                    && !user.getPhone().equals(AppConstant.PHONE_GOVERNOR)
                    && !user.getCompany().getCode().equals(AppConstant.COMPANY_CODE_DEFAULT)) {
                errorCode = userBus.checkUserLoginLog(user, model.getDeviceInfo(), platform);
            }
            if (errorCode == ErrorCode.NO_ERROR) {
                return new SuccessResponse(loginSuccess(user, bundleId));
            } else {
                return new ErrorResponse(errorCode);
            }
        } catch (Throwable e) {
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * Login success
     *
     * @param user
     * @return
     * @throws BusinessException
     */
    private UserDTO loginSuccess(User user, String bundleId) throws BusinessException, FirebaseAuthException {
        UserDTO userDTO = new UserDTO(user);
        userDTO.setToken(TokenUtility.genToken(BusinessUtility.getUserIdentifier(userDTO)));
        userDTO.setFbId(user.getFbId());
        userDTO.setGgId(user.getGgId());
        userDTO.setFirebaseToken(FirebaseAuth.getInstance().createCustomToken(String.valueOf(user.getId())));
        userDTO.setCompany(userBus.getCompanyDetail(user.getCompany().getId()));
        userDTO.setBranch(userBus.getBranch(user.getId(), user.getCompany().getId(), bundleId));
        saveLoginSessionToCache(userDTO);
        return userDTO;
    }

    @GetMapping(path = "/{userId}/profile")
    public ApiResponse getProfile(@PathVariable Long userId, HttpServletRequest request) {
        try {
            String bundleId = request.getHeader(AppConstant.HEADER_X_APP_TYPE);
            String deviceId = request.getHeader(AppConstant.HEADER_X_DEVICE_ID);
            UserDTO model = userBus.getProfile(userId, deviceId, bundleId);
            return new SuccessResponse(model);
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/edit")
    public ApiResponse editProfile(@RequestBody CreateUserModel userModel, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            ErrorCode errorCode = userBus.editProfile(userModel, userInfo);
            return new SuccessResponse(errorCode, true);
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        } catch (IllegalArgumentException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/notification")
    public ApiResponse getNotifications(@RequestBody NotificationFilter filter) {
        try {
            return new SuccessResponse(userBus.getNotifications(filter.getUserId(), filter.getType(),
                    filter.getPaging()));
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "count/new/notification")
    public ApiResponse countNewNotification(ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            } else {
                return new SuccessResponse(userBus.countNewNotification(userInfo.getUserId()));
            }
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "search/notification")
    public ApiResponse searchNotification(@RequestBody NotificationFilter filter) {
        try {
            return new SuccessResponse(userBus.searchNotification(filter.getUserId(),
                    filter.getType(), filter.getStringSearch()));
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/m/config")
    public ApiResponse getConfig(@RequestBody AppConfigFilter filter) {
        try {
            return new SuccessResponse(userBus.getMobileConfig(filter));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "forget-password")
    public ApiResponse forgetPassword(@RequestBody CreateUserModel model) {
        try {
            if (!userBus.forgetPassword(model)) {
                return new ErrorResponse("Invalidate user", ErrorCode.INVALID_ACCOUNT);
            } else {
                String password = StringUtility.generateRandomString();
                String encodedPassword = encodePassword(password);
                return new SuccessResponse(userBus.resetPasswordAndSendNotifyEmail(model.getEmail(), password,
                        encodedPassword));
            }
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/password/change")
    public ApiResponse requestChangePass(@RequestBody ChangePasswordModel model, ModelMap modelMap) {
        try {
            if (model.getForgotPassword() != null) {
                return new SuccessResponse(userBus.changePassByEmail(model.getEmail(),
                        encodePassword(model.getNewPass())));
            } else {
                UserInfo userInfo = getUserInfo(modelMap);
                User user = userBus.getUser(userInfo.getUserId());
                if (user != null && user.getPassword() != null && passwordEncoder.matches(model.getOldPass(), user.getPassword())) {
                    user.setPassword(model.getOldPass());
                    userBus.updateUser(user);
                } else if (user == null) {
                    return new ErrorResponse(ErrorCode.INVALID_ACCOUNT);
                } else if (!passwordMatch(model.getOldPass(), user.getPassword())) {
                    return new SuccessResponse(false);
                }
                return new SuccessResponse(userBus.requestChangePass(userInfo.getUserId(), model.getOldPass(),
                        encodePassword(model.getNewPass())));
            }
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/signup")
    public ApiResponse signUp(@RequestBody CreateUserModel userModel, HttpServletRequest request) {
        try {
            User userEmail = null;
            User userPhone = null;
            Company company = null;
            if (StringUtility.isNotEmpty(userModel.getPhone())) {
                userPhone = userBus.getUserByPhone(userModel.getPhone());
                if (!userModel.getRegisterCode().equals(AppConstant.COMPANY_CODE_DEFAULT)
                        && userPhone != null
                        && userPhone.getCompany().getCode().equals(AppConstant.COMPANY_CODE_DEFAULT)) {
                    if (userBus.deleteUserExperience(userPhone.getId())) {
                        userPhone = null;
                    }
                }
            }
            if (StringUtility.isNotEmpty(userModel.getEmail())) {
                userEmail = userBus.getUserByEmail(userModel.getEmail());
                if (!userModel.getRegisterCode().equals(AppConstant.COMPANY_CODE_DEFAULT)
                        && userEmail != null
                        && userEmail.getCompany().getCode().equals(AppConstant.COMPANY_CODE_DEFAULT)) {
                    if (userBus.deleteUserExperience(userEmail.getId())) {
                        userEmail = null;
                    }
                }
            }
            if (StringUtility.isNotEmpty(userModel.getRegisterCode())) {
                company = userBus.getCompany(userModel.getRegisterCode());
            }
            if (userModel != null && StringUtility.isNotEmpty(userModel.getPassword())) {
                userModel.setPassword(encodePassword(userModel.getPassword()));
            }
            if (userPhone != null) {
                return new ErrorResponse("account existed", ErrorCode.USER_EXIST_TRY_LOGIN_FAIL);
            } else if (userEmail != null) {
                return new ErrorResponse("account existed", ErrorCode.EMAIL_EXIST_TRY_LOGIN_FAIL);
            } else if (company == null) {
                return new ErrorResponse("company not existed", ErrorCode.COMPANY_NOT_EXIST);
            } else {
                String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
                UserDTO userDTO = userBus.signUp(userModel, company, platform);
                if (userDTO.getStatus() == StatusType.ACTIVE.getValue()) {
                    userDTO.setToken(TokenUtility.genToken(BusinessUtility.getUserIdentifier(userDTO)));
                    userDTO.setFirebaseToken(FirebaseAuth.getInstance().createCustomToken(String.valueOf(userDTO.getId())));
                    saveLoginSessionToCache(userDTO);
                }
                return new SuccessResponse(userDTO);
            }
        } catch (BusinessException | FirebaseAuthException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/notification/view", produces = "application/json")
    public ApiResponse viewNotificationOfUser(@RequestBody UserViewNotification model, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.viewNotification(model, userInfo));
        } catch (BusinessException e) {
            return new ErrorResponse(e);
        }
    }

    @GetMapping(path = "view/notification/all", produces = "application/json")
    public ApiResponse viewAllNotification(ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.viewAllNotification(userInfo));
        } catch (BusinessException e) {
            return new ErrorResponse(e);
        }
    }

//    @PostMapping(path = "log-me-in/facebook")
//    public ApiResponse logMeInWithFacebook(@RequestBody CreateUserModel model) {
//        try {
//            User user = userBus.logMeInWithSocial(model, LoginType.FACEBOOK);
//            if (user != null) {
//                return new SuccessResponse(loginSuccess(user));
//            }
//            return new ErrorResponse(ErrorCode.USER_EXIST_TRY_LOGIN_FAIL);
//        } catch (Throwable e) {
//            return new ErrorResponse(e.getMessage());
//        }
//    }

//    @PostMapping(path = "log-me-in/google")
//    public ApiResponse logMeInWithGoogle(@RequestBody CreateUserModel model) {
//        try {
//            User user = userBus.logMeInWithSocial(model, LoginType.GOOGLE);
//            if (user != null) {
//                return new SuccessResponse(loginSuccess(user));
//            }
//            return new ErrorResponse(ErrorCode.USER_EXIST_TRY_LOGIN_FAIL);
//        } catch (Throwable e) {
//            return new ErrorResponse(e.getMessage());
//        }
//    }

    @PostMapping(path = "upload/avatar")
    public ApiResponse uploadAvatar(@RequestParam("file") MultipartFile file,
                                    @RequestParam("userId") String userId,
                                    ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            if (file.isEmpty()) {
                return new ErrorResponse("File empty", ErrorCode.SYSTEM_ERROR);
            }
            if (StringUtility.isNotEmpty(userId)) {
                return new SuccessResponse(userBus.uploadAvatar(file, Long.valueOf(userId)));
            }
            return new SuccessResponse(userBus.uploadAvatar(file, userInfo.getUserId()));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @PostMapping(path = "send-otp")
    public ApiResponse sendOTP(@RequestBody OTPFilter filter) {
        try {
            if (filter.getPhone() == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.sendOTP(filter));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage());
        }
    }

    @PostMapping(path = "confirm-otp")
    public ApiResponse confirmOTP(@RequestBody OTPFilter filter) {
        try {
            User user = userBus.getUser(filter.getUserId());
            if (user == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.confirmOTP(filter, user.getId()));
        } catch (BusinessException e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "device")
    public ApiResponse postUserDeviceInfo(@RequestBody UserDeviceModel model, ModelMap modelMap, HttpServletRequest request) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            String platform = request.getHeader(AppConstant.HEADER_X_PLATFORM);
            UserDevice userDevice = userBus.registerDeviceInfo(model, userInfo, platform);
            return new SuccessResponse(userDevice);
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "delete/device")
    public ApiResponse deleteUserDeviceInfo(ModelMap modelMap, @RequestBody UserDeviceModel model) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            ErrorCode errorCode = userBus.deleteUserDevice(userInfo, model.getDeviceToken());
            return new SuccessResponse(errorCode, true);
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/list/conversation")
    public ApiResponse getListConversation(@RequestBody ConversationFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.getConversation(filter, userInfo));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @PostMapping(path = "/member/conversation")
    public ApiResponse getMemberOfConversation(@RequestBody ConversationFilter filter,
                                               ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.getMemberOfConversation(filter, userInfo));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @PostMapping(path = "/conversation/create")
    public ApiResponse createConversation(@RequestBody ConversationFilter filter,
                                          ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.createConversation(filter, userInfo));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @PostMapping(path = "conversation/{conversationId}/media/upload")
    public ApiResponse uploadConversationResource(@RequestParam("file") MultipartFile file,
                                                  @PathVariable Long conversationId, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (file.isEmpty()) {
                return new ErrorResponse("File empty", ErrorCode.SYSTEM_ERROR);
            }
            return new SuccessResponse(userBus.uploadResourceConversation(file, conversationId, userInfo.getUserId()));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @PostMapping(path = "conversation/{conversationId}/avatar/upload")
    public ApiResponse uploadAvatarConversation(@RequestParam("file") MultipartFile file,
                                                @PathVariable Long conversationId, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (file.isEmpty()) {
                return new ErrorResponse("File empty", ErrorCode.SYSTEM_ERROR);
            }
            return new SuccessResponse(userBus.uploadAvatarConversation(file, conversationId, userInfo.getUserId()));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }


    @GetMapping(path = "conversation/{conversationId}/delete")
    public ApiResponse deleteConversation(@PathVariable("conversationId") Long conversationId, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.deleteConversation(conversationId, userInfo));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @PostMapping(path = "conversation/{conversationId}/edit")
    public ApiResponse editConversation(@PathVariable("conversationId") Long conversationId, @RequestBody ConversationFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.editConversation(conversationId, filter, userInfo));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @PostMapping(path = "conversation/search")
    public ApiResponse searchConversation(@RequestBody ConversationFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.searchConversation(filter, userInfo));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @PostMapping(path = "conversation/check")
    public ApiResponse checkExistConversation(@RequestBody ConversationFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.checkExistConversation(filter, userInfo));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @PostMapping(path = "chat/list")
    public ApiResponse getListUserChat(@RequestBody UserFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.getListUserChat(filter, userInfo.getUserId()));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @GetMapping(path = "conversation/active/check")
    public ApiResponse checkConversationActive(ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.checkConversationActive(userInfo));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @GetMapping(path = "firebase/token")
    public ApiResponse getFirebaseTokenNew(ModelMap modelMap) throws FirebaseAuthException {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            return new SuccessResponse(userBus.getFirebaseTokenNew(userInfo));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "personal/{imageSide}/upload")
    public ApiResponse uploadUserResource(@RequestParam("file") MultipartFile file,
                                          @RequestParam("userId") String userId,
                                          @PathVariable Integer imageSide,
                                          ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (file.isEmpty()) {
                return new ErrorResponse("File empty", ErrorCode.SYSTEM_ERROR);
            }
            if (StringUtility.isNotEmpty(userId)) {
                return new SuccessResponse(userBus.uploadUserResource(file, imageSide, Long.valueOf(userId)));
            }
            return new SuccessResponse(userBus.uploadUserResource(file, imageSide, userInfo.getUserId()));
        } catch (Throwable e) {
            return new ErrorResponse(e);
        }
    }

    @GetMapping(path = "/company/{companyId}/detail")
    public ApiResponse getCompanyDetail(@PathVariable Long companyId) {
        try {
            return new SuccessResponse(userBus.getCompanyDetail(companyId));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/department/update")
    public ApiResponse updateDepartment(@RequestBody DepartmentFilter filter) {
        try {
            return new SuccessResponse(userBus.updateDepartment(filter));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/company/get")
    public ApiResponse getCompanies(@RequestBody CompanyFilter filter, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.getCompanies(filter, userInfo.getUserId()));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/company/{companyId}/branch/get")
    public ApiResponse getBranches(@PathVariable Long companyId,
                                   @RequestBody CompanyFilter filter,
                                   ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.getBranches(filter, userInfo.getUserId(), companyId));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/config/staff/{staffId}")
    public ApiResponse configStaff(@PathVariable Long staffId,
                                   @RequestBody ConfigStaffFilter filter,
                                   ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo == null) {
                return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
            }
            return new SuccessResponse(userBus.configStaff(userInfo.getUserId(), staffId, filter));
        } catch (BusinessException e) {
            return new ErrorResponse(e.getMessage(), ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping(path = "/{userId}/denied")
    public ApiResponse deniedStaff(@PathVariable Long userId, @RequestBody UserInfo user, ModelMap modelMap) {
        try {
            UserInfo userInfo = getUserInfo(modelMap);
            if (userInfo != null) {
                return new SuccessResponse(userBus.deniedStaff(userId, user.getUserType(), userInfo.getUserId()));
            }
            return new ErrorResponse(ErrorCode.AUTHENTICATE_REQUIRED);
        } catch (Throwable e) {
            return new ErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }
}
