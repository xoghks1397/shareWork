package com.sharework.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "review")

public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	@Column(name = "id")
	private long id;

	@NotNull
	@Column(name = "worker_id")
	private long workerId;

	@NotNull
	@Column(name = "giver_id")
	private long giverId;

	@Column(name = "comment")
	private String comment;

	@NotNull
	@Column(name = "star_rating")
	private String starRating;

	@Column(name = "review_type")
	private String reviewType;

	@Builder
	public Review(long workerId, long giverId, String comment, String starRating, String reviewType) {
		this.workerId = workerId;
		this.giverId = giverId;
		this.comment = comment;
		this.starRating = starRating;
		this.reviewType = reviewType;
	}
}
