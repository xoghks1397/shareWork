package com.sharework.response.model.application;

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
public class APIApplicationHistory {
	public APIApplicationHistory(Payload payload, BasicMeta meta) {
		this.payload = payload;
		this.meta = meta;
	}

	@ApiModelProperty(value = "payload", position = 1)
	public Payload payload;

	@ApiModelProperty(value = "meta", position = 2)
	public BasicMeta meta;

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

	public static class Application {

		@Id
		@Column(name = "id")
		public long id;

		@Column(name = "status")
		public String status;

		@ApiModelProperty(value = "job")
		public Job job;

		@Column(name = "isAction")
		public Boolean isAction;

		public Application(long id, String status, Job job, Boolean isAction) {
			this.id = id;
			this.status = status;
			this.job = job;
			this.isAction = isAction;
		}
	}

	public static class Job {

		@Column(name = "title")
		public String title;

		@Column(name = "start_at")
		public LocalDateTime startAt;

		@Column(name = "end_at")
		public LocalDateTime endAt;

		@Column(name = "pay_type")
		public String payType;

		@Column(name = "pay")
		public int pay;

		@ApiModelProperty(value = "user")
		public Users user;

		@ApiModelProperty(value = "jobTags")
		public List<JobTag> jobTags;

		public Job(String title, LocalDateTime startAt, LocalDateTime endAt,
				   String payType, int pay, Users user, List<JobTag> jobTags) {
			this.title = title;
			this.startAt = startAt;
			this.endAt = endAt;
			this.payType = payType;
			this.pay = pay;
			this.user = user;
			this.jobTags = jobTags;
		}
	}

	public static class Users {
		@Column(name = "profileImg")
		public String profileImg;

		public Users(String profileImg) {
			this.profileImg = profileImg;
		}
	}

	public static class JobTag {
		@Column(name = "contents")
		public String contents;

		public JobTag(String contents) { this.contents = contents; }
	}
}



