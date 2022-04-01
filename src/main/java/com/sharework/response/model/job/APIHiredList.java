package com.sharework.response.model.job;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sharework.response.model.Pagination;
import com.sharework.response.model.application.APIApplicationHistory;
import com.sharework.response.model.meta.BasicMeta;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIHiredList {
	public APIHiredList(Payload payload, Meta meta) {
		this.payload = payload;
		this.meta = meta;
	}

	@ApiModelProperty(value = "payload", position = 1)
	public Payload payload;

	@ApiModelProperty(value = "meta", position = 2)
	public Meta meta;

	public class Payload {
		@ApiModelProperty(value = "applications")
		public List<Application> applications;

		public Payload(List<Application> applications) {
			this.applications = applications;
		}
	}

	public static class Meta extends BasicMeta {
		@ApiModelProperty(value = "totalPerson")
		public Integer totalPerson;

		public Meta(boolean status, String message, Integer totalPerson) {
			super(status, message);
			this.totalPerson = totalPerson;
		}
	}

	public static class Application {

		@Id
		@Column(name = "id")
		public long id;

		@Column(name = "startAt")
		public LocalDateTime startAt;

		@Column(name = "endAt")
		public LocalDateTime endAt;

		@Column(name = "status")
		public String status;

		@Column(name = "isAction")
		public Boolean isAction;

		@ApiModelProperty(value = "user")
		public User user;

		public Application(long id, LocalDateTime startAt, LocalDateTime endAt, String status, Boolean isAction, User user) {
			this.id = id;
			this.startAt = startAt;
			this.endAt = endAt;
			this.status = status;
			this.isAction = isAction;
			this.user = user;
		}
	}

	public static class User {
		@Column(name = "id")
		public long id;

		@Column(name = "name")
		public String name;

		@Column(name = "profileImg")
		public String profileImg;

		public User(long id, String name, String profileImg) {
			this.id = id;
			this.name = name;
			this.profileImg = profileImg;
		}
	}
}



