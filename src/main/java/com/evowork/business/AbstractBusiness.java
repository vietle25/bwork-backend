package com.evowork.business;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.evowork.config.ApplicationConfig;
import com.evowork.entity.User;
import com.evowork.repository.UserRepository;
import com.evowork.viewmodel.cache.UserInfo;

public abstract class AbstractBusiness {
	
    @Autowired
    protected ApplicationConfig appConfig;
    
    @Autowired
    protected UserRepository userRepo;
    
    protected Date sysdate;
    protected Timestamp sysTimestamp;
    protected String username;
    

    protected class LoggingInfo {
        private Timestamp sysTimestamp;
        private UserInfo userInfo;
        private User user;

        public LoggingInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }

        public Timestamp getSysTimestamp() {
            return sysTimestamp;
        }

        public void setSysTimestamp(Timestamp sysTimestamp) {
            this.sysTimestamp = sysTimestamp;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    protected void initialize() {
        sysdate = new Date();
        sysTimestamp = new Timestamp(sysdate.getTime());
        username = "WEB";
    }

	protected LoggingInfo initialize(UserInfo userInfo) {
		LoggingInfo loggingInfo = new LoggingInfo(userInfo);
		if (userInfo.getClientTime() != null) {
			loggingInfo.setSysTimestamp(userInfo.getClientTime());
		} else {
			sysdate = new Date();
			loggingInfo.setSysTimestamp(new Timestamp(sysdate.getTime()));
		}
		Optional<User> user = userRepo.findById(userInfo.getUserId());
		loggingInfo.setUser(user.get());
		return loggingInfo;
	}
}
