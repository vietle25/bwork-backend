package com.evowork.viewmodel.cache;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author tuannd
 * @date 18/07/2016
 * @since 1.0
 */
public class UserInfo implements Serializable {
    private Long userId;
    private String userIdentifier;
    private Integer userType;
    private Timestamp clientTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Timestamp getClientTime() {
        return clientTime;
    }

    public void setClientTime(Timestamp clientTime) {
        this.clientTime = clientTime;
    }
}
