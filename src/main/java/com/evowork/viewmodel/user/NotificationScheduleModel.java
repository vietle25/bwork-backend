package com.evowork.viewmodel.user;

import java.sql.Timestamp;

import com.evowork.entity.NotificationSchedule;
import com.evowork.enumeration.TargetType;

/**
 * NotificationSchedule model
 * 
 * @author Tuan
 *
 */
public class NotificationScheduleModel {

	private long id; // Id
	private TargetType type; // Item type
	private Timestamp createdAt; // Create at
	private UserDTO user; //User
	private String content; //Content
	private Boolean isView; //Is View

	public NotificationScheduleModel(NotificationSchedule notification) {
		this.id = notification.getId();
		this.type = notification.getTargetType();
		this.createdAt = notification.getCreatedAt();
		if (notification.getCreatedBy() != null) {
			this.user = new UserDTO(notification.getCreatedBy());
		}
		this.content = notification.getContent();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public TargetType getType() {
		return type;
	}

	public void setType(TargetType type) {
		this.type = type;
	}

	public Boolean getView() {
		return isView;
	}

	public void setView(Boolean view) {
		isView = view;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsView() {
		return isView;
	}

	public void setIsView(Boolean isView) {
		this.isView = isView;
	}
}
