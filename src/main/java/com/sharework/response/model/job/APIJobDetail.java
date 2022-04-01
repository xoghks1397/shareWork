package com.sharework.response.model.job;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sharework.response.model.meta.BasicMeta;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIJobDetail {

	public APIJobDetail(Payload payload, Meta meta) {
		this.payload = payload;
		this.meta = meta;
	}

	@ApiModelProperty(value = "payload", position = 1)
	public Payload payload;

	@ApiModelProperty(value = "meta", position = 2)
	public Meta meta;

	public static class Payload {
		@ApiModelProperty(value = "job")
		public Job job;

		public Payload(Job job) {
			this.job = job;
		}
	}

	public static class Meta extends BasicMeta {
		@ApiModelProperty(value = "_can_apply_job")
		public Boolean _can_apply_job;

		public Meta(boolean status, String message, boolean _can_apply_job) {
			super(status, message);
			this._can_apply_job = _can_apply_job;
		}
	}

	public static class Job {

		@Id
		@Column(name = "id")
		public long id;

		@Column(name = "title")
		public String title;

		@Column(name = "start_at")
		public LocalDateTime startAt;

		@Column(name = "end_at")
		public LocalDateTime endAt;

		@Column(name = "lat")
		public double lat;

		@Column(name = "lng")
		public double lng;

		@Column(name = "address")
		public String address;

		@Column(name = "address_detail")
		public String addressDetail;

		@Column(name = "personnel")
		public long personnel;

		@Column(name = "pay_type")
		public String payType;

		@Column(name = "pay")
		public int pay;

		@Column(name = "contents")
		public String contents;

		@Column(name = "status")
		public String status;

		@ApiModelProperty(value = "user")
		public User user;

		@ApiModelProperty(value = "jobBenefits")
		public List<JobBenefit> jobBenefits;

		@ApiModelProperty(value = "jobTags")
		public List<JobTag> jobTags;

		@ApiModelProperty(value = "jobChecklists")
		public List<JobChecklist> jobChecklists;

		public Job(long id, String title, LocalDateTime startAt, LocalDateTime endAt, double lat, double lng,
				   String address, String addressDetail, long personnel, String payType, int pay, String contents,
				   String status, User user, List<JobBenefit> jobBenefits, List<JobTag> jobTags, List<JobChecklist> jobChecklists) {
			this.id = id;
			this.title = title;
			this.startAt = startAt;
			this.endAt = endAt;
			this.lat = lat;
			this.lng = lng;
			this.address = address;
			this.addressDetail = addressDetail;
			this.personnel = personnel;
			this.payType = payType;
			this.pay = pay;
			this.contents = contents;
			this.status = status;
			this.user = user;
			this.jobBenefits = jobBenefits;
			this.jobTags = jobTags;
			this.jobChecklists = jobChecklists;
		}
	}

	public static class User {
		@Column(name = "name")
		public final String name;

		@Column(name = "profile_mg")
		public String profileImg;

		public User(String name, String profileImg) {
			this.name = name;
			this.profileImg = profileImg;
		}
	}

	public static class JobBenefit {
		@Column(name = "contents")
		public String contents;

		public JobBenefit(String contents) { this.contents = contents; }
	}

	public static class JobTag {
		@Column(name = "id")
		public long id;

		@Column(name = "contents")
		public String contents;

		public JobTag(long id, String contents) {
			this.id = id;
			this.contents = contents;
		}
	}

	public static class JobChecklist {
		@Column(name = "id")
		public long id;

		@Column(name = "contents")
		public String contents;

		public JobChecklist(long id, String contents) {
			this.id = id;
			this.contents = contents;
		}
	}
}



