package com.evowork.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "user_seen_object")
public class UserSeenObject {

	@EmbeddedId
	private Pk pk;

	@Column(name = "seen_at")
	private Timestamp seenAt;

	@ManyToOne()
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	public Pk getPk() {
		return pk;
	}

	public void setPk(Pk pk) {
		this.pk = pk;
	}

	public Timestamp getSeenAt() {
		return seenAt;
	}

	public void setSeenAt(Timestamp seenAt) {
		this.seenAt = seenAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Embeddable
	public static class Pk implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "table_name")
		private String tableName;

		@Column(name = "table_id")
		private Long tableId;

		public static long getSerialVersionUID() {
			return serialVersionUID;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public Long getTableId() {
			return tableId;
		}

		public void setTableId(Long tableId) {
			this.tableId = tableId;
		}
	}
}
