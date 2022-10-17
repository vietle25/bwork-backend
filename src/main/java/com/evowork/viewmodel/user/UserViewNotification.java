package com.evowork.viewmodel.user;

import java.util.List;

/**
 * Created by Tuan on 7/10/18.
 */
public class UserViewNotification {

    private List<Long> notificationIds; //NotificationSchedule id

    public List<Long> getNotificationIds() {
        return notificationIds;
    }

    public void setNotificationIds(List<Long> notificationIds) {
        this.notificationIds = notificationIds;
    }
}
