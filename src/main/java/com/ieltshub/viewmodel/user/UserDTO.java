package com.ieltshub.viewmodel.user;

import com.ieltshub.entity.User;
import com.ieltshub.enumeration.StatusType;
import com.ieltshub.enumeration.UserType;
import com.ieltshub.viewmodel.company.CompanyModel;
import com.ieltshub.viewmodel.department.DepartmentModel;
import com.ieltshub.viewmodel.location.BranchModel;

import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the user database table.
 */
public class UserDTO {

    private Long id; //Id
    private String name; //Name
    private String phone; //Mobile phone
    private String address; //Address
    private String fbId; //Id social
    private String ggId; //Id social
    private Integer gender; //Gender
    private String token; //Token
    private String rememberToken; //Remember token
    private Timestamp birthDate; //Birth date
    private String email; //Email
    private String domicile;
    private String personalId;
    private UserType userType;
    private String avatarPath; //Avatar
    private Integer status;
    private String firebaseToken;
    private Timestamp startWorkTime;
    private List<UserResourceModel> userResources;
    private UserLoginLogModel userLoginLog;
    private CompanyModel company;
    private DepartmentModel department;
    private BranchModel branch;
    private CategoryModel staff;
    private String personId;
    private Long conversationId;

    public UserDTO(User user) {
        id = user.getId();
        email = user.getEmail();
        domicile = user.getDomicile();
        personalId = user.getPersonalId();
        phone = user.getPhone();
        name = user.getName();
        birthDate = user.getBirthDate();

        if (user.getCompany() != null) {
            this.company = new CompanyModel(user.getCompany());
        }

        if (user.getGender() != null) {
            gender = user.getGender().getValue();
        }
        address = user.getAddress();
        userType = user.getUserType();
        if (user.getAvatarPath() != null) {
            avatarPath = user.getAvatarPath();
        }
        if (user.getStatus() != null) {
            status = user.getStatus().getValue();
        }

        fbId = user.getFbId();
        ggId = user.getGgId();
        personId = user.getPersonId();
        startWorkTime = user.getStartWorkTime();
    }

    public UserDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getGgId() {
        return ggId;
    }

    public void setGgId(String ggId) {
        this.ggId = ggId;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public Timestamp getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(Timestamp startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public List<UserResourceModel> getUserResources() {
        return userResources;
    }

    public void setUserResources(List<UserResourceModel> userResources) {
        this.userResources = userResources;
    }

    public UserLoginLogModel getUserLoginLog() {
        return userLoginLog;
    }

    public void setUserLoginLog(UserLoginLogModel userLoginLog) {
        this.userLoginLog = userLoginLog;
    }

    public CompanyModel getCompany() {
        return company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }

    public BranchModel getBranch() {
        return branch;
    }

    public void setBranch(BranchModel branch) {
        this.branch = branch;
    }

    public CategoryModel getStaff() {
        return staff;
    }

    public void setStaff(CategoryModel staff) {
        this.staff = staff;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }
}