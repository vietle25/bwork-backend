package com.ieltshub.business;

import com.google.firebase.auth.FirebaseAuthException;
import com.ieltshub.common.ErrorCode;
import com.ieltshub.entity.Company;
import com.ieltshub.entity.User;
import com.ieltshub.entity.UserDevice;
import com.ieltshub.enumeration.LoginType;
import com.ieltshub.exception.BusinessException;
import com.ieltshub.viewmodel.cache.UserInfo;
import com.ieltshub.viewmodel.common.PaginationResult;
import com.ieltshub.viewmodel.common.Paging;
import com.ieltshub.viewmodel.company.CompanyFilter;
import com.ieltshub.viewmodel.company.CompanyModel;
import com.ieltshub.viewmodel.conversation.ConversationFilter;
import com.ieltshub.viewmodel.conversation.ConversationMemberModel;
import com.ieltshub.viewmodel.conversation.ConversationModel;
import com.ieltshub.viewmodel.department.DepartmentFilter;
import com.ieltshub.viewmodel.location.BranchModel;
import com.ieltshub.viewmodel.user.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author tuannd
 * @date 10/03/2016
 * @since 1.0
 */
public interface UserBus {

    /**
     * Get user login log
     *
     * @param user
     * @param deviceInfo
     * @param platform
     * @return
     */
    ErrorCode checkUserLoginLog(User user, DeviceInfo deviceInfo, String platform);

    /**
     * Get user by email
     *
     * @param email
     * @return
     */
    User getUserByEmail(String email, Integer userType);

    /**
     * Get user by phone
     *
     * @param phone
     * @return
     */
    User getUserByPhone(String phone, Integer userType);

    /**
     * Get user by email
     *
     * @param email
     * @return
     */
    User getUserByEmail(String email);

    /**
     * Get user by phone
     *
     * @param phone
     * @return
     */
    User getUserByPhone(String phone);

    /**
     * Delete user experience
     *
     * @param userId
     * @return
     */
    Boolean deleteUserExperience(Long userId);

    /**
     * Get company
     *
     * @param code
     * @return
     */
    Company getCompany(String code);

    /**
     * Get company code by user id
     *
     * @param userId
     * @return
     */
    String getCompanyCodeByUserId(Long userId);

    /**
     * update user
     *
     * @param user
     * @return
     * @since 1.0
     */
    User updateUser(User user);

    /**
     * Get profile
     *
     * @param userId
     * @return
     * @throws BusinessException
     */
    UserDTO getProfile(Long userId, String deviceId, String bundleId) throws BusinessException;

    /**
     * Edit profile
     *
     * @param userModel
     * @param userInfo
     * @return
     */
    ErrorCode editProfile(CreateUserModel userModel, UserInfo userInfo) throws BusinessException;

    /**
     * Get notification
     *
     * @param userId
     * @param type
     * @param paging
     * @return
     * @throws BusinessException
     */
    PaginationResult<NotificationModel> getNotifications(Long userId, Integer type, Paging paging) throws BusinessException;

    /**
     * Get mobile config
     *
     * @return
     * @throws BusinessException
     */
    List<AppConfigModel> getMobileConfig(AppConfigFilter filter) throws BusinessException;

    /**
     * Validate email
     *
     * @return
     * @throws BusinessException
     */
    Boolean forgetPassword(CreateUserModel userModel) throws BusinessException;

    /**
     * Get user
     *
     * @param userId
     * @return
     * @throws BusinessException
     */
    User getUser(Long userId) throws BusinessException;

    /**
     * Change password
     *
     * @return
     * @throws BusinessException
     */
    Boolean requestChangePass(Long userId, String oldPass, String newPass) throws BusinessException;

    /**
     * Sign up
     *
     * @param userModel
     * @return
     */
    UserDTO signUp(CreateUserModel userModel, Company company, String platform) throws BusinessException;

    /**
     * View notification
     *
     * @param model
     * @param userInfo
     * @return
     * @throws BusinessException
     */
    Boolean viewNotification(UserViewNotification model, UserInfo userInfo) throws BusinessException;

    /**
     * Login with social
     *
     * @param model
     * @param loginType
     * @return
     * @throws BusinessException
     */
    User logMeInWithSocial(CreateUserModel model, LoginType loginType) throws BusinessException;

    /**
     * Upload avatar
     *
     * @param file
     * @param userId
     * @return
     */
    String uploadAvatar(MultipartFile file, Long userId);

    /**
     * Upload vehicle resource
     *
     * @param file
     * @param userId
     * @return
     */
    String uploadUserResource(MultipartFile file, Integer imageSide, Long userId);

    /**
     * View notification
     *
     * @param filter
     * @return
     * @throws BusinessException
     */
    OTPModel sendOTP(OTPFilter filter) throws BusinessException;

    /**
     * Confirm OTP
     *
     * @param filter
     * @param userId
     * @return
     * @throws BusinessException
     */
    Boolean confirmOTP(OTPFilter filter, Long userId) throws BusinessException;

    /**
     * Request password - forgot pass
     *
     * @param phone, newPass
     * @return
     * @throws BusinessException
     */
    Boolean changePassByEmail(String phone, String newPass);

    /**
     * Register user devices
     *
     * @param model
     * @param userInfo
     * @param platform
     * @return
     * @throws BusinessException
     */
    UserDevice registerDeviceInfo(UserDeviceModel model, UserInfo userInfo, String platform) throws BusinessException;

    /**
     * Delete user device
     *
     * @param userInfo
     * @param deviceToken
     * @return
     * @throws BusinessException
     */
    ErrorCode deleteUserDevice(UserInfo userInfo, String deviceToken) throws BusinessException;

    /**
     * return number new notification
     *
     * @param userId
     * @return
     */
    Integer countNewNotification(Long userId) throws BusinessException;

    /**
     * search notification
     *
     * @param userId
     * @param type
     * @param stringSearch
     * @return
     */
    List<NotificationModel> searchNotification(Long userId, Integer type, String stringSearch);

    /**
     * view all notification
     *
     * @param userInfo
     * @return
     */
    Boolean viewAllNotification(UserInfo userInfo) throws BusinessException;

    /**
     * Get information member chat
     *
     * @param filter
     * @param userInfo
     * @return
     */
    List<ConversationMemberModel> getMemberOfConversation(ConversationFilter filter, UserInfo userInfo) throws BusinessException;

    /**
     * Create conversation
     *
     * @param filter
     * @param userInfo
     * @return
     */
    ConversationMemberModel createConversation(ConversationFilter filter, UserInfo userInfo) throws BusinessException;

    /**
     *
     * @param filter
     * @param userInfo
     * @return
     * @throws BusinessException
     */
    List<ConversationModel> getConversation(ConversationFilter filter, UserInfo userInfo) throws BusinessException;

    /**
     * Upload image for conversation
     *
     * @param file
     * @param conversationId
     * @return
     * @throws BusinessException
     */
    String uploadResourceConversation(MultipartFile file, Long conversationId, Long userId) throws BusinessException;

    /**
     * Upload avatar for conversation
     *
     * @param file
     * @param conversationId
     * @return
     * @throws BusinessException
     */
    String uploadAvatarConversation(MultipartFile file, Long conversationId, Long userId) throws BusinessException;

    /**
     * Delete Conversation
     *
     * @param conversationId
     * @param userInfo
     * @return
     */
    Boolean deleteConversation(Long conversationId, UserInfo userInfo) throws BusinessException;

    /**
     *
     * @param conversationId
     * @param filter
     * @param userInfo
     * @return
     * @throws BusinessException
     */
    Boolean editConversation(Long conversationId, ConversationFilter filter, UserInfo userInfo) throws BusinessException;

    /**
     * Search conversation
     *
     * @param filter
     * @param userInfo
     * @return
     */
    List<ConversationMemberModel> searchConversation(ConversationFilter filter, UserInfo userInfo) throws BusinessException;

    /**
     * Check exist conversation id
     *
     * @param filter
     * @param userInfo
     * @return
     */
    Long checkExistConversation(ConversationFilter filter, UserInfo userInfo) throws BusinessException;

    /**
     * Check exist once only my conversation
     *
     * @param userInfo
     * @return
     */
    Boolean checkConversationActive(UserInfo userInfo) throws BusinessException;

    /**
     * Get firebase token
     *
     * @param userInfo
     * @return
     * @throws BusinessException
     * @throws FirebaseAuthException
     */
    String getFirebaseTokenNew(UserInfo userInfo) throws BusinessException, FirebaseAuthException;

    /**
     * Reset and send notify
     *
     * @param email
     * @param newRawPassword
     * @param encodedPassword
     * @return
     * @throws BusinessException
     */
    Boolean resetPasswordAndSendNotifyEmail(String email, String newRawPassword, String encodedPassword) throws
            BusinessException;

    /**
     * Get company detail
     *
     * @param companyId
     * @return
     * @throws BusinessException
     */
    CompanyModel getCompanyDetail(Long companyId) throws BusinessException;

    /**
     * Get branch
     *
     * @param userId
     * @param userId
     * @return
     * @throws BusinessException
     */
    BranchModel getBranch(Long userId, Long companyId, String bundleId) throws BusinessException;

    /**
     * Update department
     *
     * @param filter
     * @return
     * @throws BusinessException
     */
    Boolean updateDepartment(DepartmentFilter filter) throws BusinessException;

    /**
     * Get companies
     *
     * @param filter
     * @return
     * @throws BusinessException
     */
    PaginationResult<CompanyModel> getCompanies(CompanyFilter filter, Long userId) throws BusinessException;

    /**
     * Get list user invite chat group
     * @param filter
     * @return
     * @throws BusinessException
     */
    PaginationResult<UserDTO> getListUserChat(UserFilter filter, Long userId) throws BusinessException;

    /**
     * Get branches
     *
     * @param filter
     * @return
     * @throws BusinessException
     */
    PaginationResult<BranchModel> getBranches(CompanyFilter filter, Long userId, Long companyId) throws BusinessException;

    /**
     * Config staff
     *
     * @param userId
     * @param staffId
     * @param filter
     * @return
     * @throws BusinessException
     */
    Boolean configStaff(Long userId, Long staffId, ConfigStaffFilter filter) throws BusinessException;

    /**
     * @param userId
     * @param isStatus
     * @param userInfo
     * @return
     * @throws BusinessException
     */
    Boolean deniedStaff(Long userId, Integer isStatus, Long userInfo) throws BusinessException;
}
