package com.evowork.repository;

import com.evowork.entity.Notification;
import com.evowork.viewmodel.common.PaginationResult;
import com.evowork.viewmodel.common.Paging;

/**
 * NotificationSchedule repository
 *
 * @author tuanlt
 * @date 15/10/2017
 * @since 1.0
 */
public interface NotificationRepositoryExtend {

	/**
	 * Get notifications
	 * @param userId
	 * @param type
	 * @param paging
	 * @return
	 */
	PaginationResult<Notification> getNotifications(Long userId, Integer type, Paging paging);
}
