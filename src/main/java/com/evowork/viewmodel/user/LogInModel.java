package com.evowork.viewmodel.user;

/**
 * @author tuannd
 * @date 05/08/2016
 * @since 1.0
 */
public class LogInModel {
    private Long userId;
    private String email; // email or phone
    private String password; // password
    private String phone; // phone
    private String rememberMeToken;
    private int loginFrom; // Login from
    private int mobileType; //Mobile type
    private String tokenFirebase; //Token firebase
	private DeviceInfo deviceInfo;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

    public String getRememberMeToken() {
        return rememberMeToken;
    }

    public void setRememberMeToken(String rememberMeToken) {
        this.rememberMeToken = rememberMeToken;
    }

	public int getLoginFrom() {
		return loginFrom;
	}

	public void setLoginFrom(int loginFrom) {
		this.loginFrom = loginFrom;
	}

	public int getMobileType() {
		return mobileType;
	}

	public void setMobileType(int mobileType) {
		this.mobileType = mobileType;
	}

	public String getTokenFirebase() {
		return tokenFirebase;
	}

	public void setTokenFirebase(String tokenFirebase) {
		this.tokenFirebase = tokenFirebase;
	}

	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
}
