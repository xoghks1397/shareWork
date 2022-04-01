package com.sharework.response.model.job;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sharework.response.model.Pagination;
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
public class APIAppliedList {
	public APIAppliedList(Payload payload, Meta meta) {
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

		@ApiModelProperty(value = "pagination")
		public Pagination pagination;

		public Payload(List<Application> applications, Pagination pagination) {
			this.applications = applications;
			this.pagination = pagination;
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

		@Column(name = "status")
		public String status;

		@ApiModelProperty(value = "user")
		public User user;

		@ApiModelProperty(value = "applicationChecklists")
		public List<ApplicationChecklist> applicationChecklists;

		public Application(long id, String status, User user, List<ApplicationChecklist> applicationChecklists) {
			this.id = id;
			this.status = status;
			this.user = user;
			this.applicationChecklists = applicationChecklists;
		}
	}

	public static class User {
		@Column(name = "id")
		public long id;

		@Column(name = "name")
		public String name;

		@Column(name = "profileImg")
		public String profileImg;

		@Column(name = "completed")
		public Integer completed;

		@Column(name = "noShow")
		public Integer noShow;

		public User(long id, String name, String profileImg, Integer completed, Integer noShow) {
			this.id = id;
			this.name = name;
			this.profileImg = profileImg;
			this.completed = completed;
			this.noShow = noShow;
		}
	}

	public static class ApplicationChecklist {
		@Column(name = "contents")
		public String contents;

		@Column(name = "response")
		public Boolean response;

		public ApplicationChecklist(String contents, Boolean response) {
			this.contents = contents;
			this.response = response;
		}
	}
}



